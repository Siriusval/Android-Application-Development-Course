package fi.jamk.launchmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showMap(view: View){
        val lat = editTextLat.text.toString().toDouble();
        val long = editTextLong.text.toString().toDouble();

        // create location with geo protocol (latitude and logitude zero, use address)
        val location = Uri.parse("geo:${lat},${long}")
        // create intent with action view and use above location
        val mapIntent = Intent(Intent.ACTION_VIEW, location)

        // verify that intent it resolves in device
        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)
        val isIntentSafe: Boolean = activities.isNotEmpty()

        // start an activity if it's safe
        if (isIntentSafe) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show();
        }

    }
}
