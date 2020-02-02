package fi.jamk.citymapexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

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
        mMap = googleMap

        // Add  markers and move camera
        val mapMarkers = mutableMapOf<String,LatLng>()
        mapMarkers["Helsinki"] = LatLng(60.170833,24.9375)
        mapMarkers["Tempere"] = LatLng(61.5,23.766667)
        mapMarkers["Turku"] = LatLng(60.45,22.266667)
        mapMarkers["Oulu"] = LatLng(65.014167,25.471944)
        mapMarkers["Jyvaskyla"] = LatLng(62.241667,25.741667)

        mapMarkers.forEach {
            mMap.addMarker(MarkerOptions().position(it.value).title("Marker in ${it.key}"))
        }

        val zoomLevel = 5.0
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapMarkers["Jyvaskyla"], zoomLevel.toFloat()))
}
}
