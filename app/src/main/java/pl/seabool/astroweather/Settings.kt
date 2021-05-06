package pl.seabool.astroweather

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private var latitude: EditTextPreference? = null
        private var longitude: EditTextPreference? = null
        private var listener: OnSharedPreferenceChangeListener? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            latitude = preferenceManager.findPreference(getString(R.string.latitude_key))
            longitude = preferenceManager.findPreference(getString(R.string.longitude_key))
            latitude!!.setOnBindEditTextListener { editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL }
            longitude!!.setOnBindEditTextListener { editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL }
            createListener()
        }

        private fun createListener() {
            listener = OnSharedPreferenceChangeListener { sharedPreferences, _ ->
                val latitudePref = sharedPreferences.getString(context?.getString(R.string.latitude_key), "NULL")
                val longitudePref = sharedPreferences.getString(context?.getString(R.string.longitude_key), "NULL")
                latitude!!.setOnBindEditTextListener { editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL }
                longitude!!.setOnBindEditTextListener { editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL }

                if (latitudePref != null) {
                    if (latitudePref == "" || latitudePref.takeLast(1) == ".") {
                        latitude?.text = getString(R.string.default_decimal)
                        Toast.makeText(activity, R.string.bad_input_toast, Toast.LENGTH_LONG).show()
                    }
                }
                if (longitudePref != null) {
                    if (longitudePref == "" || longitudePref.takeLast(1) == ".") {
                        longitude?.text = getString(R.string.default_decimal)
                        Toast.makeText(activity, R.string.bad_input_toast, Toast.LENGTH_LONG).show()
                    }
                }
            }
            PreferenceManager.getDefaultSharedPreferences(context)
                    .registerOnSharedPreferenceChangeListener(listener)
        }
    }
}