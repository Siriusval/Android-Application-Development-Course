package fi.jamk.showgolfcoursesinmap

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.marker_info.view.*
import org.json.JSONArray


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val baseUrl = "https://ptm.fi/materials/golfcourses/"

    //Use the JSON data to set the markers on the map
    fun setMarkers(golfCourses :JSONArray){


         for( i in 0 until golfCourses.length()) {

             val course = GolfCourse(golfCourses.getJSONObject(i))

             val marker = this.mMap.addMarker(MarkerOptions().position(course.location))
             marker.tag = course

             when(course.type){
                 "Kulta" -> marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                 "Kulta/Etu" -> marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                 "Etu" -> marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                 "?" -> marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
             }

         }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //HTTP request
        val queue = Volley.newRequestQueue(this)
        val url = baseUrl+"golf_courses.json"

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->

                //retrieve
                val golfCourses = response.getJSONArray("courses")
                //set markers on map
                setMarkers(golfCourses)
                //center camera
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(62.241667,25.741667), 5.0.toFloat()))

                Log.d("JSON", golfCourses.toString())
            },
            Response.ErrorListener { error ->
                Log.d("JSON", error.toString())

            }
        ){}

        queue.add(jsonObjectRequest)

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

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        //define custom info window
        this.mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
        override fun getInfoWindow(marker: Marker): View? {
            return null;
        }

        override fun getInfoContents(marker: Marker): View? {
            val info = layoutInflater.inflate(R.layout.marker_info,null) as LinearLayout
            val course: GolfCourse = marker.tag as GolfCourse

            info.titleTextView.text = course.name
            info.addressTextView.text = course.address
            info.phoneTextView.text = course.phone
            info.emailTextView.text = course.email
            info.websiteTextView.text = course.web
            val url =baseUrl+course.image
            Log.d("URL",url)

            val listener = object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    e?.printStackTrace();
                    return false;
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if ( marker.isInfoWindowShown) {
                        marker.hideInfoWindow();
                        marker.showInfoWindow();
                    }
                    return false;
                }

            }

            GlideApp.with(applicationContext).load(url).listener(listener).into(info.imageView)



            return info
        }


    })


    }


}
