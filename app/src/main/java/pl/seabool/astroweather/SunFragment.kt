package pl.seabool.astroweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import java.util.*

class SunFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val astroCalculator = AstroCalculator(setAstroDate(), setLocation(51.759247, 19.455982))

        setDataToTextViews(astroCalculator)
    }

    private fun getAstroDateTimeText(adt: AstroDateTime): String {
        val time = StringBuilder()
        time.append(adt.hour)
        time.append(":")
        time.append(adt.minute)
        return time.toString()
    }

    private fun setAstroDate(): AstroDateTime {
        val currentTime = Calendar.getInstance()

        val year = currentTime.get(Calendar.YEAR)
        val month = currentTime.get(Calendar.MONTH) + 1 //months are indexed from 0
        val day = currentTime.get(Calendar.DAY_OF_MONTH)

        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val second = currentTime.get(Calendar.SECOND)

        return AstroDateTime(year, month, day, hour, minute, second, 1, true)
    }

    private fun setLocation(latitude: Double, longtitude: Double): AstroCalculator.Location {
        return AstroCalculator.Location(latitude, longtitude)
    }

    private fun setDataToTextViews(astroCalculator: AstroCalculator) {
        view?.findViewById<TextView>(R.id.sunrise_time)?.text =
            getAstroDateTimeText(astroCalculator.sunInfo.sunrise)
        view?.findViewById<TextView>(R.id.sunrise_azimuth)?.text =
            astroCalculator.sunInfo.azimuthRise.toString()

        view?.findViewById<TextView>(R.id.sunset_time)?.text =
            getAstroDateTimeText(astroCalculator.sunInfo.sunset)
        view?.findViewById<TextView>(R.id.sunset_azimuth)?.text =
            astroCalculator.sunInfo.azimuthSet.toString()

        view?.findViewById<TextView>(R.id.morning_time)?.text =
            getAstroDateTimeText(astroCalculator.sunInfo.twilightMorning)
        view?.findViewById<TextView>(R.id.evening_time)?.text =
            getAstroDateTimeText(astroCalculator.sunInfo.twilightEvening)
    }


}