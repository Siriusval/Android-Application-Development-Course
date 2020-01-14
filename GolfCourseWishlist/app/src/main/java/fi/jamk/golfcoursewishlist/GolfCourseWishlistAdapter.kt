/**
 * GoldCourseWishlistAdapter Adapter
 * extends from RecyclerView.Adapter
 *
 * Describe behaviour of RecyclerView
 */
package fi.jamk.golfcoursewishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_places.view.*

class GolfCourseWishlistAdapter(private val places: ArrayList<Place>)
    : RecyclerView.Adapter<GolfCourseWishlistAdapter.ViewHolder>() {

    /**
     * View Holder class to hold UI views
     * contains attributes for each individual views
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.placeName
        val imageView: ImageView = view.placeImage
    }

    /**
     * create UI View Holder from XML layout
     * Get inflater and put .xml file in it
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GolfCourseWishlistAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_places, parent, false)
        return ViewHolder(view)
    }


    /**
     * return item count in employees
     */
    override fun getItemCount(): Int {
        return places.size
    }

    /**
     * bind data to UI View Holder (bind item attributes to view)
     * For each item, get Place object, then put the name in the textView, and the picture on the glide item
     */
    override fun onBindViewHolder(holder: GolfCourseWishlistAdapter.ViewHolder, position: Int) {
        // place to bind UI
        val place: Place = places[position]
        // name
        holder.nameTextView.text = place.name
        // image
        Glide.with(holder.imageView.context).load(place.getImageResourceId(holder.imageView.context)).into(holder.imageView)
    }


}