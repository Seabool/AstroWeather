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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentTime = Calendar.getInstance()

        val year = currentTime.get(Calendar.YEAR)
        val month = currentTime.get(Calendar.MONTH) + 1 //months are indexed from 0
        val day = currentTime.get(Calendar.DAY_OF_MONTH)

        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val second = currentTime.get(Calendar.SECOND)


        val astroDateTime = AstroDateTime(year, month, day, hour, minute, second, 1, true)
        val astroLocation = AstroCalculator.Location(51.759247, 19.455982)
        val astroCalculator = AstroCalculator(astroDateTime, astroLocation)

        getView()?.findViewById<TextView>(R.id.sunrise_time)?.text = getAstroDateTimeText(astroCalculator.sunInfo.sunrise)
        getView()?.findViewById<TextView>(R.id.sunrise_azimuth)?.text = astroCalculator.sunInfo.azimuthRise.toString()

        getView()?.findViewById<TextView>(R.id.sunset_time)?.text = getAstroDateTimeText(astroCalculator.sunInfo.sunset)
        getView()?.findViewById<TextView>(R.id.sunset_azimuth)?.text = astroCalculator.sunInfo.azimuthSet.toString()

        getView()?.findViewById<TextView>(R.id.morning_time)?.text = getAstroDateTimeText(astroCalculator.sunInfo.twilightMorning)
        getView()?.findViewById<TextView>(R.id.evening_time)?.text = getAstroDateTimeText(astroCalculator.sunInfo.twilightEvening)
    }

    private fun getAstroDateTimeText(adt:AstroDateTime):String{
        val time = StringBuilder()
        time.append(adt.hour)
        time.append(":")
        time.append(adt.minute)
        return time.toString()
    }


}