package brainstorm.android

import java.io.File
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.text.TextWatcher
import android.widget.EditText

class MapTextFragment(startText: Seq[String]) extends Fragment {

  var text: EditText = _

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View = {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.maptext_fragment, parent, false)
  }

  override def onViewCreated(view : View, savedInstanceState : Bundle) {
      // Setup any handles to view objects here
      // addTextChangedListener(textWatcher)
      text = view.findViewById(R.id.mapEditText).asInstanceOf[EditText]
      text.setText(startText.foldLeft("")((prev, now) => prev ++ "\n" ++ now))
  }

  def addListener(textWatcher: TextWatcher) =
    text.addTextChangedListener(textWatcher)
  


}
