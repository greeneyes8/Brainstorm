package brainstorm.android

import java.io.File
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class MapFragment(startText: Seq[String]) extends Fragment {


    override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
        savedInstanceState: Bundle): View = {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.mapfragment, parent, false)
    }

    override def onViewCreated(view : View, savedInstanceState : Bundle) {
        val fragmentManager = getFragmentManager()
        fragmentManager.beginTransaction()
                       .replace(R.id.mapContent, new MapTextFragment(startText))
                       .replace(R.id.mapDrawer, new MapDrawerFragment())
                       .commit()
    }

}