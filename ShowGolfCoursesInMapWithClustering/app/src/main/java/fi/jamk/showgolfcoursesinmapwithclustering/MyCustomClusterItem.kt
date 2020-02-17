package fi.jamk.showgolfcoursesinmapwithclustering

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Custom ClusterItem implementation
 */
class MyCustomClusterItem(private var course: GolfCourse) : ClusterItem {

    //VARS (& inline CONSTRUCTOR)
    private var mPosition: LatLng = this.course.location
    private var mTitle: String = this.course.name
    private var mSnippet: String = ""


    //OVERRIDE

    override fun getSnippet(): String {
        return this.mSnippet
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getPosition(): LatLng {
        return mPosition
    }

    //GET
    fun getGolfCourse(): GolfCourse {
        return course
    }

}