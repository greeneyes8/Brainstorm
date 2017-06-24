package brainstorm.android

import android.app.Fragment
import android.os.Bundle
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater 

class AuthorsFragment extends Fragment {
    override def onCreate(savedInstanceState : Bundle) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
      savedInstanceState: Bundle): View = {
        return inflater.inflate(R.layout.authors_fragment, parent, false)
    }

    override def onCreateOptionsMenu(menu : Menu, inflater : MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }

    override def onViewCreated(view : View, savedInstanceState : Bundle) {
      val pInfo = getActivity.getPackageManager.getPackageInfo(getActivity.getPackageName(), 0)
      val versionStr = pInfo.versionName
      val versionView = getActivity.findViewById(R.id.versioncode).asInstanceOf[TextView]
      versionView.setText(versionStr)
    }
}
