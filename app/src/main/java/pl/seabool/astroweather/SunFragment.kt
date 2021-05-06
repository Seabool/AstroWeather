package pl.seabool.astroweather

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.astrocalculator.AstroCalculator

class SunFragment : Fragment() {

    private lateinit var astroData: AstroData
    private var handler: Handler? = Handler(Looper.getMainLooper())
    private var handlerTask: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        astroData = AstroData()
        updateFromPreferences()
    }

    override fun onResume() {
        updateFromPreferences()
        super.onResume()
    }

    private fun setDataToTextViews(astroCalculator: AstroCalculator) {
        view?.findViewById<TextView>(R.id.sunrise_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.sunrise)
        view?.findViewById<TextView>(R.id.sunrise_azimuth)?.text =
            astroData.getAzimuthToString(astroCalculator.sunInfo.azimuthRise)

        view?.findViewById<TextView>(R.id.sunset_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.sunset)
        view?.findViewById<TextView>(R.id.sunset_azimuth)?.text =
            astroData.getAzimuthToString(astroCalculator.sunInfo.azimuthSet)

        view?.findViewById<TextView>(R.id.morning_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.twilightMorning)
        view?.findViewById<TextView>(R.id.evening_time)?.text =
            astroData.getAstroTimeText(astroCalculator.sunInfo.twilightEvening)
    }

    //TODO: try to move it to AstroData
    private fun updateFromPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val latitude = sharedPreferences.getString(
            getString(R.string.latitude_key),
            getString(R.string.default_decimal)
        )!!.toDouble()
        val longitude = sharedPreferences.getString(
            getString(R.string.longitude_key),
            getString(R.string.default_decimal)
        )!!.toDouble()
        val interval = sharedPreferences.getString(
            getString(R.string.interval_key),
            getString(R.string.default_interval)
        )!!.toLong()
        astroData.updatePosition(latitude, longitude)
        setDataToTextViews(astroData.astroCalculator)
        refreshView(interval)
    }

    //TODO: try to move it to AstroData
    private fun refreshView(seconds: Long) {
        handlerTask?.let { handler!!.removeCallbacks(it) }
        handlerTask = Runnable {
            setDataToTextViews(astroData.astroCalculator)
            handler!!.postDelayed(handlerTask!!, seconds * 1000)
        }
        handlerTask!!.run()
    }
}