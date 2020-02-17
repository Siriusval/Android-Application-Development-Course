package fi.jamk.showgolfcoursesinmapwithclustering

import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject

/**
 * GolfCourse Object
 * Easier to manipulate than JSON
 */
class GolfCourse(golfCourseData: JSONObject) {

    //VARIABLES (& inline CONSTRUCTOR)
    var type: String = golfCourseData["type"].toString()
    private var lat: Double = golfCourseData["lat"].toString().toDouble()
    private var lng: Double = golfCourseData["lng"].toString().toDouble()
    var name: String = golfCourseData["course"].toString()
    var address: String = golfCourseData["address"].toString()
    var phone: String = golfCourseData["phone"].toString()
    var email: String = golfCourseData["email"].toString()
    var web: String = golfCourseData["web"].toString()
    var image: String = golfCourseData["image"].toString()
    var text: String = golfCourseData["text"].toString()
    var location: LatLng = LatLng(this.lat, this.lng)


}