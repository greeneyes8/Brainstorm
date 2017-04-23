package brainstorm.android

import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

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
        val fileList = Array("Raz", "Dwa", "Trzy", "Cztery", "Five", "Sechs", "Siebien",
          "Raz", "Dwa", "Trzy", "Cztery", "Five", "Sechs", "Siebien",
          "Raz", "Dwa", "Trzy", "Cztery", "Five", "Sechs", "Siebien")
        val mmListView: RecyclerView = getActivity.findViewById(R.id.mindMapList).asInstanceOf[RecyclerView]
        val mmListAdapter = new MindMapAdapter(fileList)
        mmListView.setLayoutManager(new LinearLayoutManager(getActivity()))
        mmListView.setAdapter(mmListAdapter)
    }
}
