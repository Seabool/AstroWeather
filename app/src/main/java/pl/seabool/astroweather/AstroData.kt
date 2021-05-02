package pl.seabool.astroweather

import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import java.util.*

class AstroData {
    var astroCalculator: AstroCalculator
        private set

    private fun setAstroDate(): AstroDateTime {
        val currentTime = Calendar.getInstance()
        val year = currentTime[Calendar.YEAR]
        val month = currentTime[Calendar.MONTH] + 1 //months are indexed from 0
        val day = currentTime[Calendar.DAY_OF_MONTH]
        val hour = currentTime[Calendar.HOUR_OF_DAY]
        val minute = currentTime[Calendar.MINUTE]
        val second = currentTime[Calendar.SECOND]
        return AstroDateTime(year, month, day, hour, minute, second, 1, true)
    }

    private fun setLocation(
        latitude: Double,
        longitude: Double
    ): AstroCalculator.Location {

        return AstroCalculator.Location(latitude, longitude)
    }

    fun updatePosition(latitude: Double, longtitude: Double) : AstroCalculator{
        astroCalculator = AstroCalculator(setAstroDate(), setLocation(latitude, longtitude))
        return astroCalculator
    }

    fun getAstroTimeText(adt: AstroDateTime): String {
        val time = StringBuilder()
        time.append(adt.hour)
        time.append(":")
        time.append(adt.minute)
        return time.toString()
    }

    fun getAstroDateText(adt: AstroDateTime): String {
        val date = StringBuilder()
        date.append(adt.day)
        date.append(".")
        date.append(adt.month)
        date.append(".")
        date.append(adt.year)
        return date.toString()
    }

    init {
        astroCalculator = AstroCalculator(setAstroDate(), setLocation(0.0, 0.0))
    }


}