package fi.jamk.employeesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data - remember use your own data here
        val url = "https://api.jsonbin.io/b/5e1ee55b5df640720835bfb0/1"

        // Create request and listeners
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Get employees from JSON
                val employees = response.getJSONArray("employees")
                recyclerView.adapter = EmployeesAdapter(employees)
            },
            Response.ErrorListener { error ->
                Log.d("JSON",error.toString())
            }
        ) {

            // override getHeader to pass key to service
            override fun getHeaders(): MutableMap<String, String> {

                val headers = mutableMapOf<String, String>()
                headers["secret-key"] = "\$2b\$10\$pSGSneXMtmM17gsESAtFI.oSg31nZOtWzXBbrZuzKzT8h1/Sn9gfm"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }


}
