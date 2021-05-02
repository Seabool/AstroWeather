package pl.seabool.astroweather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.astrocalculator.AstroCalculator

class SunFragment : Fragment() {

    private lateinit var astroData: AstroData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        astroData = AstroData()
        val astroCalculator = astroData.astroCalculator
        setDataToTextViews(astroCalculator)
    }

    override fun onResume() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context /* Activity context */)
        val latitude = sharedPreferences.getString("latitude_key", "0.0")!!.toDouble()
        val longitude = sharedPreferences.getString("longitude_key", "0.0")!!.toDouble()
        val interval = sharedPreferences.getString("interval_key", "300")!!.toInt()

        Log.i("latitude", latitude.toString())
        Log.i("latitude", longitude.toString())
        Log.i("latitude", interval.toString())

        astroData.updatePosition(latitude, longitude)
        setDataToTextViews(astroData.astroCalculator)
        super.onResume()
    }

    private fun setDataToTextViews(astroCalculator: AstroCalculator) {
        view?.findViewById<TextView>(R.id.sunrise_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.sunrise)
        view?.findViewById<TextView>(R.id.sunrise_azimuth)?.text =
            astroCalculator.sunInfo.azimuthRise.toInt().toString().plus("°")

        view?.findViewById<TextView>(R.id.sunset_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.sunset)
        view?.findViewById<TextView>(R.id.sunset_azimuth)?.text =
            astroCalculator.sunInfo.azimuthSet.toInt().toString().plus("°")

        view?.findViewById<TextView>(R.id.morning_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.twilightMorning)
        view?.findViewById<TextView>(R.id.evening_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.twilightEvening)


    }
}