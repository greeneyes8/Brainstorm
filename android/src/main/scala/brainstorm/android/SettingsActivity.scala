package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.content.res.Configuration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.content.SharedPreferences.OnSharedPreferenceChangeListener 
import android.graphics.Color 
import android.widget.Toast
import TypedResource._

class SettingsActivity extends DrawerLayoutActivity 
  with TypedFindView with OnSharedPreferenceChangeListener {
 //   lazy val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    override def onCreate(savedInstanceState: Bundle): Unit = {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        //afterOnCreate(savedInstanceState)
        setTitle(R.string.action_GeneralSettings)
        setFragment(new SettingsFragment())
    }
   override def onSharedPreferenceChanged(sharedPreferences: SharedPreferences,
          key: String) {
      recreate()
   }


}
