package fi.jamk.golfcoursewishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /* --- VARIABLES --- */

    //boolean to know if it's list view, if false this is grid view
    private var isListView = true

    //GridManager to change number of column
    private var mStaggeredLayoutManager: StaggeredGridLayoutManager? = null

    /**
     * Layout manipulation
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Use StaggeredGridLayoutManager as a layout manager for recyclerView
        mStaggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = mStaggeredLayoutManager

        // Use GolfCourseWishlistAdapter as a adapter for recyclerView
        //It transform list of places into displayable items
        recyclerView.adapter = GolfCourseWishlistAdapter(Places.placeList())
    }

    /**
     * Menu bar manipulation
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        //get the inflater to modify menu bar
        val inflater: MenuInflater = menuInflater

        //add menu.xml to menu bar
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * Execute action when an menu item is selected
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get id of the menuItem
        return when (item.itemId) {
            R.id.action_toggle -> {
                //if click on listView icon, change to gridView
                if (isListView) {
                    item.setIcon(R.drawable.ic_view_stream_white_24dp)
                    item.title = "Show as list"
                    isListView = false
                    mStaggeredLayoutManager?.spanCount = 2
                //if click on grid icon, change back to listView
                } else {
                    item.setIcon(R.drawable.ic_view_column_white_24dp)
                    item.title = "Show as grid"
                    isListView = true
                    mStaggeredLayoutManager?.spanCount = 1
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
