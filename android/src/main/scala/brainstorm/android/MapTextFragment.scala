package brainstorm.android

import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.text.TextWatcher
import android.widget.EditText

class MapTextFragment(startText: Seq[String], textWatcher: TextWatcher) extends Fragment {
  var text: EditText = _

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View = {
      val result = inflater.inflate(R.layout.maptext_fragment, parent, false)
      result
  }

  override def onViewCreated(view : View, savedInstanceState : Bundle) {
      text = view.findViewById(R.id.mapEditText).asInstanceOf[EditText]
      if (!startText.isEmpty)
        text.setText(startText.tail.foldLeft(startText.head)((prev, now) => prev ++ "\n" ++ now))
      addListener(textWatcher)
  }

  def addListener(textWatcher: TextWatcher) =
    text.addTextChangedListener(textWatcher)
}
