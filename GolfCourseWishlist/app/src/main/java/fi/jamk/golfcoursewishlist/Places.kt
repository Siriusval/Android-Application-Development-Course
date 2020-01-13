package fi.jamk.golfcoursewishlist

class Places {
    // like "static" in other OOP languages
    companion object {
        // hard code a few places
        var placeNameArray = arrayOf(
            "Black Mountain",
            "Chambers Bay",
            "Clear Water",
            "Harbour Town",
            "Muirfield",
            "Old Course",
            "Pebble Beach",
            "Spy Class",
            "Turtle Bay"
        )

        // return places
        fun placeList(): ArrayList<Place> {
            val list = ArrayList<Place>()
            for (i in placeNameArray.indices) {
                val place = Place()
                place.name = placeNameArray[i]
                place.image = placeNameArray[i].replace("\\s+".toRegex(), "").toLowerCase()
                list.add(place)
            }
            return list
        }
    }
}