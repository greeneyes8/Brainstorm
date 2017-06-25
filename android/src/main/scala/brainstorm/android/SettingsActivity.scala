package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.content.SharedPreferences.OnSharedPreferenceChangeListener 
import TypedResource._

class SettingsActivity extends DrawerLayoutActivity 
  with TypedFindView with OnSharedPreferenceChangeListener {
  override def onCreate(savedInstanceState: Bundle) {
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    mySharedPreferences.registerOnSharedPreferenceChangeListener(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    setTitle(R.string.action_GeneralSettings)
    setFragment(new SettingsFragment())
  }

  override def onSharedPreferenceChanged(sharedPreferences: SharedPreferences,
    key: String) {
      recreate
  }
}
