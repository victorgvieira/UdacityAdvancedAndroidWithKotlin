package com.example.android.wander

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //  DONE Step 1.0: Rename mMap to map
    private lateinit var map: GoogleMap

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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
}
