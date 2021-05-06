package pl.seabool.astroweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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

        setDataToTextViews(astroData.astroCalculator)
    }

    override fun onResume() {
        setDataToTextViews(astroData.astroCalculator)
        super.onResume()
    }

    fun setAstroData(astroData: AstroData) {
        this.astroData = astroData
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

}