package com.example.android.wander

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //  DONE Step 1.0: Rename mMap to map
    private lateinit var map: GoogleMap

    // DONE Step 6.9 Create a TAG class variable. This will be used for logging purposes
    private val TAG = MapsActivity::class.java.simpleName

    /* IMPORTANTNOTE run gradle -> app -> Tasks -> android -> signingReport to reveal your SHA1 value and use in the URL
      https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=YOUR_SHA1_HERE%3Bcom.example.android.wander*/

    // DONE Step 1.1: Open the debug version of the google_maps_api.xml file.
    //  The file includes a comment with a long URL. The URL's parameters include specific information about your app.

    //  DONE Step 1.2 Copy and paste the URL into a browser.

    //  DONE Step 1.3 Follow the prompts to create a project in the Google API Console.
    //   Because of the parameters in the provided URL, the API Console knows to automatically enable the Google Maps Android API.

    //  DONE Step 1.4 Create an API key and click Restrict Key to restrict the key's use to Android apps. The generated API key should start with AIza.

    //  DONE Step 1.5 In the google_maps_api.xml file, paste the key into the google_maps_key string where it says YOUR_KEY_HERE.

    //  DONE Step 2.0 create a new menu named file map_options
    //  DONE Step 2.1 in map_options add the item options for each map type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // DONE Step 3.0: remove the code that places the marker in Sydney and moves the camera
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        // DONE Step 3.1: find the coordinates of your home
        // DONE Step 3.2: Create a value for the latitude and a value for the longitude and input their float values
        val latitude = -7.234729
        val longitude = -39.412622
        // DONE Step 3.3 Create a new LatLng object using the values you just created
        // NOTE Zoom level detail 1: World; 5: Landmass/continent; 10: City; 15: Streets; 20: Buildings
        val homeLatLng = LatLng(latitude, longitude)
        // DONE Step 3.3 Create a val for how zoomed in you want to be on the map
        val zoomLevel = 15f
        // DONE Step 3.4 Move the camera calling the moveCamera() function on the GoogleMap object
        //  and pass homeLatLng and zoomLevel in a CameraUpdate object using CameraUpdateFactory.newLatLngZoom()
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        // DONE Step 3.5 Create a marker that point to your home.
        val homeMarker = MarkerOptions().position(homeLatLng)
        // DONE Step 3.6 Add the marker to the map
        map.addMarker(homeMarker)

        // DONE Step 4.3 call setMapLongClick. Pass in map
        setMapLongClick(map)

        // DONE Step 5.5 call setPoiClick. Pass in map.
        setPoiClick(map)

        // DONE Step 6.17 call the setMapStyle passing in your GoogleMap object.
        setMapStyle(map)
    }

    // DONE Step 2.3: override the onCreateOptionsMenu() method
    //  and inflate the menu from the map_options resource file
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        Since we are handling the menu creation, the super call is not needed
//        return super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    // DONE Step 2.4: override the onOptionsItemSelected() method
    //  use the setMapType() method on the GoogleMap object, passing in one of the map-type constants
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    // DONE Step 4.0 Create a method called setMapLongClick() that takes a GoogleMap as an argument.
    private fun setMapLongClick(map: GoogleMap) {
        // DONE Step 4.1 Attach a long click listener to the map object.
        map.setOnMapLongClickListener { latLng ->
            // DONE Step 4.4 create a snippet (additional text that is displayed below the title)
            //  that displays the latitude and longitude of a marker
            val snippet = String.format(
                Locale.getDefault(),
                getString(R.string.lat_long_snippet),
                latLng.latitude,
                latLng.longitude
            )

            // DONE Step 4.2 call the addMarker() method passing in a new MarkerOptions object
            //  with the position set to the passed-in LatLng
            map.addMarker(
                MarkerOptions().position(latLng)
                    // DONE Step 4.5 Set the title of the marker to “Dropped Pin” and the snippet to the snippet you just created
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    // DONE Step 7.0 call the icon() method passing a BitmapDescriptorFactory to use the default marker but change the color to blue
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    // DONE Step 5.0 Create a method called setPoiClick() that takes a GoogleMap as an argument.
    private fun setPoiClick(map: GoogleMap) {
        // DONE Step 5.1 set an OnPoiClickListener on the passed-in GoogleMap
        map.setOnPoiClickListener { poi ->
            // DONE Step 5.2 create a variable called poiMarker.
            // DONE Step 5.3 place a marker at the POI location and set the title to the name of the POI.
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            // DONE Step 5.4 call showInfoWindow() on poiMarker to immediately show the info window
            poiMarker.showInfoWindow()
        }
    }

    // DONE Step 6.0 Navigate to https://mapstyle.withgoogle.com/ in your browser.
    // DONE Step 6.1 Select Create a Style.
    // DONE Step 6.2 Select the Retro theme.
    // DONE Step 6.3 Click More Options at the bottom of the menu
    // DONE Step 6.4 In the Feature type list, select Road > Fill. Change the color of the roads to any color you choose.
    // DONE Step 6.5 Click Finish. Copy the JSON code from the resulting pop-up window.

    // DONE Step 6.6 create a resource directory in the res directory and name it raw
    // DONE Step 6.7 Create a file in res/raw called map_style.json
    // DONE Step 6.8 Paste the JSON code into the new resource file

    // DONE Step 6.10 create a setMapStyle() function that takes in a GoogleMap
    private fun setMapStyle(map: GoogleMap) {
        //DONE Step 6.11 create a try/catch block to handle possible errors
        try {
            // DONE Step 6.12 call setMapStyle() on the GoogleMap object.
            // DONE Step 6.14 create a variable to store a boolean indicating the success of the styling.
            val success = map.setMapStyle(
                // DONE Step 6.13 Pass in a MapStyleOptions object, which loads the JSON file.
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            )
            // DONE Step 6.15 If the styling is unsuccessful, print a log that the parsing has failed
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }

            // DONE Step 6.16 if the file can't be loaded, the method throws a Resources.NotFoundException.
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)

        }
    }
}
