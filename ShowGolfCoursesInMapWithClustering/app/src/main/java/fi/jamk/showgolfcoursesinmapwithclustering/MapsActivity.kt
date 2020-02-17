package fi.jamk.showgolfcoursesinmapwithclustering

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import org.json.JSONArray
import org.json.JSONObject


/**
 * Main Map Activity
 *
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //STATIC
    companion object {
        /**
         * URL for APIs
         */
        const val BASE_URL = "https://ptm.fi/materials/golfcourses/"

        /**
         * Cluster manager, manage all the markers logic on map
         */
        lateinit var mClusterManager: ClusterManager<MyCustomClusterItem>
        lateinit var clickedCluster: Cluster<MyCustomClusterItem>
        lateinit var clickedClusterItem: MyCustomClusterItem
    }

    //VARS
    /**
     * GMap object, use to do operations on it (move camera, etc...)
     */
    private lateinit var mMap: GoogleMap

    /**
     * Where map is initialized
     */
    private val mapCenterPoint: LatLng = LatLng(62.241667, 25.741667)

    /**
     * Object to render clusters on map
     */
    private lateinit var mMarkerClusterRenderer: MarkerClusterRenderer


    /**
     * Link map with ClusterManager & MarkerClusterRenderer
     */
    private fun setupClusters() {
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenterPoint, 5.toFloat()))

        // Initialize the manager with the context and the map.
        mClusterManager = ClusterManager(this, mMap)

        // Initialize the renderer with the context and the map.
        mMarkerClusterRenderer = MarkerClusterRenderer(this, mMap, mClusterManager)

        //Link both together, then add manager to the GMap object
        mClusterManager.renderer = mMarkerClusterRenderer
        mMap.setInfoWindowAdapter(mClusterManager.markerManager)

        //Set custom MarkerInfoWidowView
        mClusterManager.markerCollection.setOnInfoWindowAdapter(CustomMarkerInfoWindowView(this))

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)

        mClusterManager
            .setOnClusterClickListener { cluster ->
                clickedCluster = cluster
                false
            }

        mClusterManager
            .setOnClusterItemClickListener { item ->
                clickedClusterItem = item
                false
            }


    }

    //Use the JSON data to add the clustersItems to the clusterManager
    private fun addItems(golfCourses: JSONArray) {

        for (i in 0 until golfCourses.length()) { //parse JSON

            val course = GolfCourse(golfCourses.getJSONObject(i)) //create GolfCourse object

            val customClusterItem = MyCustomClusterItem(course) //Transform it in cluster item

            mClusterManager.addItem(customClusterItem) //add
        }
        mClusterManager.cluster() //render

    }

    /**
     * Do a request to get jsonData
     */
    private fun generateVolleyRequest(): JsonObjectRequest {
        //HTTP request
        val url = BASE_URL + "golf_courses.json"

        //Add items
        return JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->

                //retrieve
                val golfCourses = response.getJSONArray("courses")

                //add items to cluster & set markers on map
                addItems(golfCourses)
            },
            Response.ErrorListener { error ->
                Log.d("JSON", error.toString())

            }
        )

    }

    /**
     * MainActivity onCreate
     */
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
        this.mMap = googleMap

        //Volley
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = generateVolleyRequest()
        queue.add(jsonObjectRequest)

        //Setup clusterer
        setupClusters()


    }


}
