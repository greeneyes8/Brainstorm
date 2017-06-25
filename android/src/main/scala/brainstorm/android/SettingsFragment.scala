package brainstorm.android

import android.app.Fragment
import android.preference.PreferenceFragment  
import android.os.Bundle
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.MenuInflater 
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.preference.Preference
import android.widget.Toast
import android.preference.Preference.OnPreferenceChangeListener 

class SettingsFragment extends PreferenceFragment {

    override def onCreate(savedInstanceState : Bundle) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        addPreferencesFromResource(R.xml.preferences)
    }

    override def onCreateOptionsMenu(menu : Menu, inflater : MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }
    
    override def onViewCreated(view : View, savedInstanceState : Bundle) { 
        val appPath : String = getActivity().getApplicationContext().getFilesDir().getAbsolutePath()
        //Toast.makeText(getActivity(), appPath, Toast.LENGTH_LONG).show()
    }
}
