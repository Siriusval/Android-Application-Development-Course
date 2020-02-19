package fi.jamk.employeesfragmentsapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fi.jamk.employeesfragmentsapp.R
import fi.jamk.employeesfragmentsapp.adapter.EmployeesAdapter
import kotlinx.android.synthetic.main.item_list.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    // true if device is in landscape mode
    private var twoPane: Boolean = false

    // employees static object - can be used as MainActivity.employees
    companion object {
        var employees: JSONArray = JSONArray()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // is device landscape mode
        if (item_detail_container != null) {
            // The detail container view will be present only in the landscape
            // If this view is present, then the activity should be in two-pane mode.
            twoPane = true
        }

        // Load employees if not loaded, if loaded setup recycler view
        if (employees.length() == 0) {
            loadJsonData()
        } else {
            setupRecyclerView()
        }

    }

    // Add layout manager and adapter to recycler view
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            EmployeesAdapter(this, twoPane)
    }

    // Load JSON from the net
    private fun loadJsonData() {


        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data - remember use your own data here
        val url = "https://api.jsonbin.io/b/5e1ee55b5df640720835bfb0/1"

        // Create request and listeners
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                // Get employees from JSON
                employees = response.getJSONArray("employees")
                setupRecyclerView()
            },
            Response.ErrorListener { error ->
                Log.d("JSON", error.toString())
            }
        ) {

            // override getHeader to pass key to service
            override fun getHeaders(): MutableMap<String, String> {

                val headers = mutableMapOf<String, String>()
                headers["secret-key"] =
                    "\$2b\$10\$pSGSneXMtmM17gsESAtFI.oSg31nZOtWzXBbrZuzKzT8h1/Sn9gfm"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

}
