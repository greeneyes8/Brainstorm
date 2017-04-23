package brainstorm.android

import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class MainFragment extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation. 

    override def onCreateView(inflater : LayoutInflater, parent : ViewGroup, savedInstanceState : Bundle) : View = {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.mainfragment, parent, false)
    }
	
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    
    override def onViewCreated(view : View, savedInstanceState : Bundle) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}