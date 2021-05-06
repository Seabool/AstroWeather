package pl.seabool.astroweather

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val adapter = ViewPagerAdapter(supportFragmentManager)
    private lateinit var astroData: AstroData
    private var handler: Handler? = Handler(Looper.getMainLooper())
    private var handlerTask: Runnable? = null
    private var sunFragment = SunFragment()
    private var moonFragment = MoonFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        astroData = AstroData()

        if (savedInstanceState != null) {
            sunFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                getString(R.string.sun)
            ) as SunFragment
            moonFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                getString(R.string.moon)
            ) as MoonFragment
        }

        updateLocation()
        updateFragmentsData()

        if (resources.getBoolean(R.bool.isTablet)) {
            if (savedInstanceState == null) {
                addTabletFragments()
            }
        } else {
            addPhoneFragments()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        supportFragmentManager.putFragment(savedInstanceState, getString(R.string.sun), sunFragment)
        supportFragmentManager.putFragment(
            savedInstanceState,
            getString(R.string.moon),
            moonFragment
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val intent = Intent(this, Settings::class.java).apply {}
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        updateLocation()
    }

    override fun onPause() {
        super.onPause()
        clearHandler()
    }

    private fun updateLocation() {
        val location = findViewById<TextView>(R.id.location)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val latitude = sharedPreferences.getString(
            getString(R.string.latitude_key),
            getString(R.string.default_decimal)
        )
        val longitude = sharedPreferences.getString(
            getString(R.string.longitude_key),
            getString(R.string.default_decimal)
        )
        location.text = getString(R.string.location_holder, latitude, longitude)
        if (latitude != null && longitude != null) {
            location.text = String.format("%.2f / %.2f", latitude.toDouble(), longitude.toDouble())
        }
        val interval = sharedPreferences.getString(
            getString(R.string.interval_key),
            getString(R.string.default_interval)
        )!!.toLong()

        if (longitude != null && latitude != null) {
            astroData.updatePosition(latitude.toDouble(), longitude.toDouble())
        }
        refreshEveryInterval(interval)
    }

    private fun addTabletFragments() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        fragmentTransaction.add(R.id.fragment_container, sunFragment, getString(R.string.sun))
        fragmentTransaction.add(R.id.fragment_container2, moonFragment, getString(R.string.moon))
        fragmentTransaction.commit()
    }

    private fun addPhoneFragments() {
        adapter.addFragment(sunFragment, getString(R.string.sun))
        adapter.addFragment(moonFragment, getString(R.string.moon))
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabs = findViewById<TabLayout>(R.id.tabs)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun refreshEveryInterval(seconds: Long) {
        clearHandler()
        handlerTask = Runnable {
            Log.i("isRefreshing", "refresh")
            updateExistingFragments()
            handler!!.postDelayed(handlerTask!!, seconds * 1000)
        }
        handlerTask!!.run()
    }

    private fun updateFragmentsData() {
        sunFragment.setAstroData(astroData)
        moonFragment.setAstroData(astroData)
    }

    private fun clearHandler() {
        handlerTask?.let { handler!!.removeCallbacks(it) }
    }

    private fun updateExistingFragments() {
        updateFragmentsData()
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        for (fragment in supportFragmentManager.fragments) {
            ft.detach(fragment)
            ft.attach(fragment)
        }
        ft.commit()

    }
}