package brainstorm.android

import java.io.File
import java.io.FileWriter
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.app.ActionBarActivity
import android.support.v7.app.ActionBarDrawerToggle
import android.widget.Toast

class MainFragment extends Fragment with NewMindMapDialogListener {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation. 
    lazy val mapsRootFile: File = new File(getActivity.getFilesDir, "maps/")
    lazy val mmListAdapter: MindMapAdapter = new MindMapAdapter(mapsRootFile, MainFragment.this)

    override def onPositive(name: String): Unit = {
      val fw: FileWriter = new FileWriter(new File(mapsRootFile, name))
      fw.close
      mmListAdapter.invalidate
    }

    private class fabClick extends View.OnClickListener {
      override def onClick(view: View) = {
        val mmdialog = new NewMindMapDialog(MainFragment.this)
        if (false) {
          val transaction = getFragmentManager.beginTransaction
          //transaction.add(android.R.id.content, mmdialog).addToBackStack(null).commit
          //mmdialog.show(transaction, "missiles")
        } else {
          mmdialog.show(getFragmentManager(), "missiles")
        }
      }
    }

    override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
      savedInstanceState: Bundle): View = {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.mainfragment, parent, false)
    }
	
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
   
    override def onViewCreated(view : View, savedInstanceState : Bundle) {
        // Setup any handles to view objects here
        if (!mapsRootFile.exists)
          mapsRootFile.mkdir
        val mmListView: RecyclerView = getActivity.findViewById(R.id.mindMapList).asInstanceOf[RecyclerView]
        val fab = getActivity.findViewById(R.id.fab)
        fab.setOnClickListener(new fabClick)
        mmListView.setLayoutManager(new LinearLayoutManager(getActivity()))
        mmListView.setAdapter(mmListAdapter)
    }
}
