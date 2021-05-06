package pl.seabool.astroweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.astrocalculator.AstroCalculator
import kotlin.math.round
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
        setDataToTextViews(astroData.astroCalculator)
    }

    override fun onResume() {
        super.onResume()
        setDataToTextViews(astroData.astroCalculator)
    }

    fun setAstroData(astroData: AstroData) {
        this.astroData = astroData
    }

    fun setDataToTextViews(astroCalculator: AstroCalculator) {
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
            (round(astroData.getMoonAge() * 100.0) / 100.0).toString()
    }
}