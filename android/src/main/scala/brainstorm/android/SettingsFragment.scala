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

class SettingsFragment extends PreferenceFragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation. 
    override def onCreate(savedInstanceState : Bundle) = {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    override def onCreateOptionsMenu(menu : Menu, inflater : MenuInflater) = {
        //Adds items to the ActionBar
        menu.clear()
        inflater.inflate(R.menu.menu_map, menu)
    }


   // override def onCreateView(inflater : LayoutInflater, parent : ViewGroup, savedInstanceState : Bundle) : View = {
    //    // Defines the xml file for the fragment
     //   return inflater.inflate(R.layout.settingsfragment, parent, false)
   // }
	
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    
    override def onViewCreated(view : View, savedInstanceState : Bundle) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        
    }
}