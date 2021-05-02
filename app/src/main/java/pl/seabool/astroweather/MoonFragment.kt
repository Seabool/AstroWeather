package pl.seabool.astroweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.astrocalculator.AstroCalculator
import kotlin.math.roundToInt

class MoonFragment : Fragment() {

    private lateinit var astroData: AstroData

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moon, container, false)
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
        view?.findViewById<TextView>(R.id.moonrise_time)?.text =
                astroData.getAstroTimeText(astroCalculator.moonInfo.moonrise)
        view?.findViewById<TextView>(R.id.moon_sunset_time)?.text =
                astroData.getAstroTimeText(astroCalculator.moonInfo.moonset)
        view?.findViewById<TextView>(R.id.new_moon_date)?.text =
                astroData.getAstroDateText(astroCalculator.moonInfo.nextNewMoon)
        view?.findViewById<TextView>(R.id.full_moon_date)?.text =
                astroData.getAstroDateText(astroCalculator.moonInfo.nextFullMoon)
        view?.findViewById<TextView>(R.id.phase_moon)?.text =
                (astroCalculator.moonInfo.illumination * 100).roundToInt().toString().plus("%")
        view?.findViewById<TextView>(R.id.synodic_month_day)?.text =
                astroCalculator.moonInfo.age.roundToInt().toString()
    }

    private fun updateFromPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context /* Activity context */)
        val latitude = sharedPreferences.getString("latitude_key", "0.0")!!.toDouble()
        val longitude = sharedPreferences.getString("longitude_key", "0.0")!!.toDouble()
        astroData.updatePosition(latitude, longitude)
        setDataToTextViews(astroData.astroCalculator)
    }

}