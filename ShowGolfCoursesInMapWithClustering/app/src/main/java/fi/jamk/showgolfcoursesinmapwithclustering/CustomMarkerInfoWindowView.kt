package fi.jamk.showgolfcoursesinmapwithclustering

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.marker_info.view.*


/**
 * Class used to define custom infoWindow when a marker is clicked
 */
class CustomMarkerInfoWindowView(private val mContext: Context) : GoogleMap.InfoWindowAdapter {

    /**
     * Our view in infoContent
     */
    private val markerItemView: View = LayoutInflater.from(mContext).inflate(
        R.layout.marker_info,
        null
    ) as LinearLayout        //Get layout for infoWindow


    /**
     * Edit infoWindow, in our case, we only want to edit the content
     */
    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    /**
     * Edit infoContent
     */
    override fun getInfoContents(marker: Marker): View? {
        //Get course object from marker tag
        val course: GolfCourse = MapsActivity.clickedClusterItem.getGolfCourse()

        //Fill layout
        this.markerItemView.titleTextView.text = course.name
        this.markerItemView.addressTextView.text = course.address
        this.markerItemView.phoneTextView.text = course.phone
        this.markerItemView.emailTextView.text = course.email
        this.markerItemView.websiteTextView.text = course.web
        val url = MapsActivity.BASE_URL + course.image

        //Custom listener for Glide (picture was not replacing placeholder)

        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("Error", "Error loading thumbnail!")
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
                /*
                if (marker != null && marker.isInfoWindowShown) {
                    marker.showInfoWindow()
                }
                */

                return false;
            }

        }

        try {
            //Load picture
            GlideApp.with(mContext).load(url).listener(listener).into(markerItemView.imageView)

        } catch (e: Error) {
            Log.d("Error", e.toString())
        }



        return markerItemView
    }


}