package fi.jamk.weatherapp

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fi.jamk.weatherapp.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), AddCityDialogFragment.AddDialogListener {

    // example call is : https://api.openweathermap.org/data/2.5/weather?q=Jyväskylä&APPID=YOUR_API_KEY&units=metric&lang=fi
    private val API_LINK: String = "https://api.openweathermap.org/data/2.5/weather?q="
    private val API_ICON: String = "https://openweathermap.org/img/w/"
    private val API_KEY: String = "9aaa13dd0c59e415d16ce752ffa32c78"

    companion object {
        var forecasts: MutableList<Forecast> = mutableListOf()

    }


    // add a few test cities
    val cities: MutableList<String> =
        mutableListOf(
            "Tours",
            "Turku",
            "Jyväskylä",
            "Helsinki",
            "Oulu",
            "Paris",
            "New York",
            "Tokyo"
        )


    // city index, used when data will be loaded
    private var index: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Load weather forecasts
        if (forecasts.size == 0) {
            loadWeatherForecast(cities[index])
        } else {
            setUI()
        }
    }

    private fun setUI() {
        // hide progress bar
        progressBar.visibility = View.INVISIBLE
        // add adapter
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        // add fab
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {

            val dialog = AddCityDialogFragment()
            dialog.show(supportFragmentManager, "AddCityDialogFragment")


        }
    }


    // load forecast
    private fun loadWeatherForecast(city: String) {
        // url for loading
        val url = "$API_LINK$city&APPID=$API_KEY&units=metric&lang=fi"

        // JSON object request with Volley
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->
                try {
                    // load OK - parse data from the loaded JSON
                    val mainJSONObject = response.getJSONObject("main")
                    val weatherArray = response.getJSONArray("weather")
                    val firstWeatherObject = weatherArray.getJSONObject(0)

                    // city, condition, temperature
                    val cityName = response.getString("name")
                    val condition = firstWeatherObject.getString("main")
                    val temperature = mainJSONObject.getString("temp") + " °C"
                    // time
                    val weatherTime: String = response.getString("dt")
                    val weatherLong: Long = weatherTime.toLong()
                    val formatter: DateTimeFormatter =
                        DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss")
                    val dt = Instant.ofEpochSecond(weatherLong).atZone(ZoneId.systemDefault())
                        .toLocalDateTime().format(formatter).toString()
                    // icon
                    val weatherIcon = firstWeatherObject.getString("icon")
                    val iconUrl = "$API_ICON$weatherIcon.png"

                    // add forecast object to the list
                    forecasts.add(Forecast(cityName, condition, temperature, dt, iconUrl))
                    // use Logcat window to check that loading really works
                    Log.d("WEATHER", "**** weatherCity = " + forecasts[index].city)
                    // load another city if not loaded yet
                    if ((++index) < cities.size) loadWeatherForecast(cities[index])
                    else {
                        Log.d("WEATHER", "*** ALL LOADED!")
                    }

                    //instantiate progress bar, viewpager and fab
                    setUI()


                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("WEATHER", "***** error: $e")
                    // hide progress bar
                    progressBar.visibility = View.INVISIBLE
                    // show Toast -> should be done better!!!
                    Toast.makeText(this, "Error loading weather forecast!", Toast.LENGTH_LONG)
                        .show()
                }
            },
            Response.ErrorListener { error -> Log.d("PTM", "Error: $error") })
        // start loading data with Volley
        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(jsonObjectRequest)
    }

    override fun onDialogPositiveClick(newCity: String) {

        // Create a Handler Object
        val handler = Handler(Handler.Callback {
            // Toast message
            Toast.makeText(applicationContext, it.data.getString("message"), Toast.LENGTH_SHORT)
                .show()

            cities.add(newCity)
            true
        })
        // Create a new Thread to insert data to database
        Thread(Runnable {

            //load forecast in mutablelist, which triggers PageViewModel update
            loadWeatherForecast(newCity)
            val message = Message.obtain()
            message.data.putString("message", "Item added to list!")
            handler.sendMessage(message)
        }).start()

    }

}