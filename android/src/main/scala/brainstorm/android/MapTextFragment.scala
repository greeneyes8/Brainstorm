package brainstorm.android

import java.io.File
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class MapTextFragment(var text: Seq[String]) extends Fragment {
  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View = {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.maptext_fragment, parent, false)
  }

  override def onViewCreated(view : View, savedInstanceState : Bundle) {
      // Setup any handles to view objects here
  }
}
