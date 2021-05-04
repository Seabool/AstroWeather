package pl.seabool.astroweather

import androidx.preference.PreferenceManager
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

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

    private fun setLocation(latitude: Double, longitude: Double): AstroCalculator.Location {
        return AstroCalculator.Location(latitude, longitude)
    }

    fun updatePosition(latitude: Double, longitude: Double): AstroCalculator {
        astroCalculator = AstroCalculator(setAstroDate(), setLocation(latitude, longitude))
        return astroCalculator
    }

    fun getAstroTimeText(adt: AstroDateTime): String {
        return String.format("%02d:%02d", adt.hour, adt.minute)
    }

    fun getAstroDateText(adt: AstroDateTime): String {
        return "${adt.day}.${adt.month}.${adt.year}"
    }

    fun getAzimuthToString(azimuth: Double): String {
        val azimuthFirst = azimuth.roundToInt()
        val azimuthSecond = abs((String.format("%.02f", azimuth - azimuthFirst).toDouble() * 100).toInt())
        return "${azimuthFirst}°${azimuthSecond}'"
    }

    fun getMoonAge() : Double{
        return astroCalculator.moonInfo.age/365*29
    }

    init {
        astroCalculator = AstroCalculator(setAstroDate(), setLocation(0.0, 0.0))
    }
}