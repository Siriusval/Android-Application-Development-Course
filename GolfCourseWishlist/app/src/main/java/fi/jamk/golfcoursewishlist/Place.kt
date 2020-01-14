/**
 * Place class
 * Define a physical golf place
 *
 */
package fi.jamk.golfcoursewishlist

import android.content.Context

class Place {

    //Name of the place
    var name: String? = null
    //image of the place (no space and lowercase)
    var image: String? = null

    /**
     * return imageId of current image var
     */
    fun getImageResourceId(context: Context): Int {
        return context.resources.getIdentifier(this.image,"drawable", context.packageName)
    }
}
