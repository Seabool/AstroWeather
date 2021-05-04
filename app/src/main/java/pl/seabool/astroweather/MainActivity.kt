package pl.seabool.astroweather

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val adapter = ViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter.addFragment(SunFragment(), getString(R.string.sun))
        adapter.addFragment(MoonFragment(), getString(R.string.moon))
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabs = findViewById<TabLayout>(R.id.tabs)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        updateLocation()
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val intent = Intent(this, Settings::class.java).apply {}
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        updateLocation()
        super.onResume()
    }

    private fun updateLocation() {
        val location = findViewById<TextView>(R.id.location)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val latitude = sharedPreferences.getString(getString(R.string.latitude_key), getString(R.string.default_decimal))
        val longitude = sharedPreferences.getString(getString(R.string.longitude_key), getString(R.string.default_decimal))
        location.text = getString(R.string.location_holder, latitude, longitude)
    }

    class ViewPagerAdapter(manager: FragmentManager) :
            FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}