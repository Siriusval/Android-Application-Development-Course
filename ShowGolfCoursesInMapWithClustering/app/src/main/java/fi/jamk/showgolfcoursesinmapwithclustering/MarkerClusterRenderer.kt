package fi.jamk.showgolfcoursesinmapwithclustering

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

/**
 * Custom renderer for markers, change color of icon
 */
class MarkerClusterRenderer(
    private val mContext: Context, map: GoogleMap?,
    clusterManager: ClusterManager<MyCustomClusterItem>
) :
    DefaultClusterRenderer<MyCustomClusterItem>(mContext, map, clusterManager) {


    //OVERRIDE

    /**
     * Change color before rendering
     */
    override fun onBeforeClusterItemRendered(
        item: MyCustomClusterItem,
        markerOptions: MarkerOptions
    ) {
        setMarkerColor(item, markerOptions)
    }

    //FUNCTIONS
    /**
     * Helper function for onBeforeClusterItemRendering, change color of marker icon
     */
    private fun setMarkerColor(item: MyCustomClusterItem, markerOptions: MarkerOptions): Unit {

        when (item.getGolfCourse().type) {
            "Kulta" -> markerOptions.icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_YELLOW
                )
            )
            "Kulta/Etu" -> markerOptions.icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN
                )
            )
            "Etu" -> markerOptions.icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_BLUE
                )
            )
            "?" -> markerOptions.icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_BLUE
                )
            )
        }

    }

}
