/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.treasureHunt

import android.Manifest
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import com.example.android.treasureHunt.databinding.ActivityHuntMainBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar

/**
 * The Treasure Hunt app is a single-player game based on geofences.
 *
 * This app demonstrates how to create and remove geofences using the GeofencingApi. Uses an
 * BroadcastReceiver to monitor geofence transitions and creates notification and finishes the game
 * when the user enters the final geofence (destination).
 *
 * This app requires a device's Location settings to be turned on. It also requires
 * the ACCESS_FINE_LOCATION permission and user consent. For geofences to work
 * in Android Q, app also needs the ACCESS_BACKGROUND_LOCATION permission and user consent.
 */

class HuntMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHuntMainBinding
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var viewModel: GeofenceViewModel

    // DONE: Step 2 add in variable to check if device is running Q or later
    //  NOTE: this step was copied from the solution code
    private var runningOnQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    // A PendingIntent for the Broadcast Receiver that handles geofence transitions.
    // DONE: Step 8 add in a pending intent
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
            .apply {
                action = ACTION_GEOFENCE_EVENT
            }
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hunt_main)
        viewModel = ViewModelProviders.of(
            this, SavedStateViewModelFactory(
                this.application,
                this
            )
        ).get(GeofenceViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        // DONE: Step 9 instantiate the geofencing client
        geofencingClient = LocationServices.getGeofencingClient(this)

        // Create channel for notifications
        createChannel(this)
    }

    override fun onStart() {
        super.onStart()
        checkPermissionsAndStartGeofencing()
    }

    /*
 *  When we get the result from asking the user to turn on device location, we call
 *  checkDeviceLocationSettingsAndStartGeofence again to make sure it's actually on, but
 *  we don't resolve the check to keep the user from seeing an endless loop.
 */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // DONE: Step 7 add code to check that the user turned on their device location and ask
        //  again if they did not
        if (requestCode == REQUEST_TURN_DEVICE_LOCATION_ON) {
            checkDeviceLocationSettingsAndStartGeofence(false)
        }
    }

    /*
     *  When the user clicks on the notification, this method will be called, letting us know that
     *  the geofence has been triggered, and it's time to move to the next one in the treasure
     *  hunt.
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val extras = intent?.extras
        if (extras != null) {
            if (extras.containsKey(GeofencingConstants.EXTRA_GEOFENCE_INDEX)) {
                viewModel.updateHint(extras.getInt(GeofencingConstants.EXTRA_GEOFENCE_INDEX))
                checkPermissionsAndStartGeofencing()
            }
        }
    }

    /*
     * In all cases, we need to have the location permission.  On Android 10+ (Q) we need to have
     * the background permission as well.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // DONE: Step 5 add code to handle the result of the user's permission
        //  NOTE: this step was copied from the solution code
        Log.d(TAG, "onRequestPermissionResult")
        if (grantResults.isEmpty()
            || grantResults[LOCATION_PERMISSION_INDEX] == PackageManager.PERMISSION_DENIED
            || (requestCode == REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
                    && grantResults[BACKGROUND_LOCATION_PERMISSION_INDEX] == PackageManager.PERMISSION_DENIED)
        ) {
            // Permission denied
            Snackbar.make(
                binding.activityMapsMain,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.settings) {
                // Displays App setting screen
                startActivity(Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }.show()
        } else {
            checkDeviceLocationSettingsAndStartGeofence()
        }
    }

    /**
     * This will also destroy any saved state in the associated ViewModel, so we remove the
     * geofences here.
     */
    override fun onDestroy() {
        super.onDestroy()
        removeGeofences()
    }

    /**
     * Starts the permission check and Geofence process only if the Geofence associated with the
     * current hint isn't yet active.
     */
    private fun checkPermissionsAndStartGeofencing() {
        if (viewModel.geofenceIsActive()) return
        if (foregroundAndBackgroundLocationPermissionApproved()) {
            checkDeviceLocationSettingsAndStartGeofence()
        } else {
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    /*
     *  Uses the Location Client to check the current state of location settings, and gives the user
     *  the opportunity to turn on location services within our app.
     */
    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        // DONE: Step 6 add code to check that the device's location is on
        // DONE: Step 6.0 create the locationRequest with low priority
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        // DONE: Step 6.1 create a builder from LocationSettingsRequest.Builder passing the location locationRequest
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        // DONE: Step 6.2 get the client settings from LocationServices.getSettingsClient to check the location setting
        val settingsClient = LocationServices.getSettingsClient(this)
        // DONE: Step 6.3 check and store the result of location settings passing in the builder
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())
        // DONE: Step 6.4 add an onFailureListener() to the locationSettingsResponseTask to find out if the location settings are not satisfied
        locationSettingsResponseTask.addOnFailureListener { exception ->
            // DONE: Step 6.5 Check if the exception is of type ResolvableApiException and if so, try calling the startResolutionForResult() method in order to prompt the user to turn on device location
            if (exception is ResolvableApiException && resolve) {
                try {
                    exception.startResolutionForResult(this, REQUEST_TURN_DEVICE_LOCATION_ON)
                } catch (sendEx: IntentSender.SendIntentException) {
//                    If calling startResolutionForResult enters the catch block, print a log
                    Log.d(TAG, "Error getting location settings resolution: ${sendEx.message}")
                }
            } else {
                // DONE: Step 6.6 If the exception is not of type ResolvableApiException, present a snackbar that alerts the user that location needs to be enabled
                Snackbar.make(
                    binding.activityMapsMain,
                    R.string.location_required_error,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettingsAndStartGeofence()
                }.show()
            }
        }
        // DONE: Step 6.7 add an addOnCompleteListener to the locationSettingsResponseTask and check that it is successful, if so, add the geofence
        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
                addGeofenceForClue()
            }
        }

    }

    /*
     *  Determines whether the app has the appropriate permissions across Android 10+ and all other
     *  Android versions.
     */
    @TargetApi(29)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        // DONE: Step 3 replace this with code to check that the foreground and background
        //  permissions were approved
        //  NOTE: this step was copied from the solution code
        // We check the same permission added in AndroidManifest
        val backgroundLocationApproved = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val foregroundLocationApproved =
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        return backgroundLocationApproved && foregroundLocationApproved
    }

    /*
     *  Requests ACCESS_FINE_LOCATION and (on Android 10+ (Q) ACCESS_BACKGROUND_LOCATION.
     */
    @TargetApi(29)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        // DONE: Step 4 add code to request foreground and background permissions
        //  NOTE: this step was copied from the solution code
        if (foregroundAndBackgroundLocationPermissionApproved()) {
            return
        }

        // Else request the permission
        // this provides the result[LOCATION_PERMISSION_INDEX]
        var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = when {
            runningOnQOrLater -> {
                // this provides the result[BACKGROUND_LOCATION_PERMISSION_INDEX]
                // Add one more permission to array
                permissions += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                // return the request code for check two permissions
                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        }
        Log.d(TAG, "Request foreground only location permission")
        ActivityCompat.requestPermissions(this, permissions, resultCode)
    }

    /*
     * Adds a Geofence for the current clue if needed, and removes any existing Geofence. This
     * method should be called after the user has granted the location permission.  If there are
     * no more geofences, we remove the geofence and let the viewmodel know that the ending hint
     * is now "active."
     */
    private fun addGeofenceForClue() {
        // DONE: Step 10 add in code to add the geofence
        // DONE: Step 10.1 check if we have any active geofences for our treasure hunt.
        //  If we already do, we shouldn't add another
        if (viewModel.geofenceIsActive())
            return

        // DONE: Step 10.2 get the currentGeofenceIndex using the viewModel.
        //  If the index is higher than the number of landmarks we have, it means the user has found all the treasures.
        //  Remove geofences
        //  call geofenceActivated on the viewModel
        //  then return
        val currentGeofenceIndex = viewModel.nextGeofenceIndex()
        if (currentGeofenceIndex >= GeofencingConstants.NUM_LANDMARKS) {
            removeGeofences()
            viewModel.geofenceActivated()
            return
        }
        // DONE: Step 10.3 Once you have the index and know it is valid, get the data surrounding the geofence
        val currentGeofenceData = GeofencingConstants.LANDMARK_DATA[currentGeofenceIndex]

        // DONE: Step 10.4 Build the geofence using the geofence builder, the information in currentGeofenceData, like
        //  the id and
        //  the latitude and longitude to create the circular region.
        //  Set the expiration duration using the constant set in GeofencingConstants.
        //  Set the transition type to GEOFENCE_TRANSITION_ENTER.
        //  Finally, build the geofence.
        val geofence = Geofence.Builder()
            .apply {
                setRequestId(currentGeofenceData.id)
                setCircularRegion(
                    currentGeofenceData.latLong.latitude,
                    currentGeofenceData.latLong.longitude,
                    GeofencingConstants.GEOFENCE_RADIUS_IN_METERS
                )
                setExpirationDuration(GeofencingConstants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            }.build()

        // DONE: Step 10.5 Build the geofence request
        //  Set the initial trigger to INITIAL_TRIGGER_ENTER,
        //  add the geofence you just built
        //  and then build
        val geofencingRequest = GeofencingRequest.Builder()
            .apply {
                setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                addGeofence(geofence)
            }.build()

        // DONE: Step 10.6 Call removeGeofences() on the geofencingClient to remove any geofences already associated to the pending intent
        geofencingClient.removeGeofences(geofencePendingIntent)?.run {
            // DONE: Step 10.7 When removeGeofences() completes, regardless of its success or failure, add the new geofences
            addOnCompleteListener {
                geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)?.run {
                    // DONE: Step 10.8 If adding the geofences is successful, let the user know through a toast that they were successful
                    addOnSuccessListener {
                        Toast.makeText(
                            this@HuntMainActivity,
                            R.string.geofences_added,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("Add Geofence", geofence.requestId)
                        viewModel.geofenceActivated()
                    }
                    // DONE: Step 10.9 If adding the geofences fails, present a toast letting the user know that there was an issue in adding the geofences.
                    addOnFailureListener {
                        Toast.makeText(
                            this@HuntMainActivity,
                            R.string.geofences_not_added,
                            Toast.LENGTH_SHORT
                        ).show()
                        it.message?.let { message ->
                            Log.w(TAG, message)
                        }
                    }
                }
            }
        }
    }

    /**
     * Removes geofences. This method should be called after the user has granted the location
     * permission.
     */
    private fun removeGeofences() {
        // TODO: Step 12 add in code to remove the geofences
    }

    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "HuntMainActivity.treasureHunt.action.ACTION_GEOFENCE_EVENT"
    }
}

private const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
private const val TAG = "HuntMainActivity"
private const val LOCATION_PERMISSION_INDEX = 0
private const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
