package brainstorm.android

import java.io.File
import java.io.FileWriter
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.AdapterView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.preference.PreferenceManager 
import android.content.SharedPreferences   

class MainFragment extends Fragment with NewMindMapDialogListener {
    lazy val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity())
    lazy val mapsRootFile: File = new File(getActivity.getFilesDir, "maps/")
    lazy val mmListAdapter: MindMapAdapter = new MindMapAdapter(mapsRootFile, this)

    override def onPositive(name: String) {
      val fw: FileWriter = new FileWriter(new File(mapsRootFile, name))
      fw.close
      mmListAdapter.invalidate
    }

    private class fabClick extends View.OnClickListener {
      override def onClick(view: View) {
        val mmdialog = new NewMindMapDialog(MainFragment.this)
        if (false) {
          val transaction = getFragmentManager.beginTransaction
        } else {
          mmdialog.show(getFragmentManager(), "missiles")
        }
      }
    }

    override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
      savedInstanceState: Bundle): View = {
        inflater.inflate(R.layout.mainfragment, parent, false)
    }
   
    override def onViewCreated(view : View, savedInstanceState : Bundle) {
        if (!mapsRootFile.exists)
          mapsRootFile.mkdir
        val mmListView: RecyclerView = getActivity.findViewById(R.id.mindMapList).asInstanceOf[RecyclerView]
        val fab = getActivity.findViewById(R.id.fab)
        fab.setOnClickListener(new fabClick())
        mmListView.setLayoutManager(new LinearLayoutManager(getActivity()))
        mmListView.setAdapter(mmListAdapter)
    }

    override def onResume() {
      super.onResume()
      mmListAdapter.invalidate
    }
}
