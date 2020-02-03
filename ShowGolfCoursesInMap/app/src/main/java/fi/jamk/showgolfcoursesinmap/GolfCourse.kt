package fi.jamk.showgolfcoursesinmap

import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject

class GolfCourse(golfCourseData : JSONObject) {

    var type : String = ""
    private var lat : Double = 0.0
    private var lng : Double = 0.0
    var name: String = ""
    var address: String = ""
    var phone: String = ""
    var email: String = ""
    var web: String = ""
    var image: String = ""
    var text: String = ""

     var location :LatLng = LatLng(Double.MIN_VALUE, Double.MIN_VALUE)
        get () = LatLng(this.lat,this.lng)

    init{
        this.type = golfCourseData["type"].toString()
        this.lat = golfCourseData["lat"].toString().toDouble()
        this.lng = golfCourseData["lng"].toString().toDouble()
        this.name = golfCourseData["course"].toString()
        this.address= golfCourseData["address"].toString()
        this.phone=  golfCourseData["phone"].toString()
        this.email= golfCourseData["email"].toString()
        this.web= golfCourseData["web"].toString()
        this.image= golfCourseData["image"].toString()
        this.text= golfCourseData["text"].toString()
    }


}