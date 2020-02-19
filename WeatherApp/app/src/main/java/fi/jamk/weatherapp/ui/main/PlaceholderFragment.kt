package fi.jamk.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import fi.jamk.weatherapp.Forecast
import fi.jamk.weatherapp.MainActivity
import fi.jamk.weatherapp.R
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_FORECAST_POSITION = "forecast_position"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(position: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FORECAST_POSITION, position)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            val position: Int = arguments!!.getInt(ARG_FORECAST_POSITION)
            setForecast(MainActivity.forecasts[position])
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        pageViewModel.forecast.observe(viewLifecycleOwner, Observer<Forecast> {
            cityTextView.text = it.city
            conditionTextView.text = it.condition
            temperatureTextView.text = it.temperature
            timeTextView.text = it.time
            Glide.with(this).load(it.icon).override(400, 400).into(iconImageView)
        })

        return root
    }


}