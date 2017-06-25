package brainstorm.android

import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.app.DialogFragment
import android.content.DialogInterface
import android.app.AlertDialog
import android.text.Editable
import android.app.Dialog
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import android.support.design.widget.TextInputLayout 

trait TextDialogListener {
  def onPositive(name: String): Unit
}

class TextDialog(listener: TextDialogListener, titleId: Int, hintId: Int)
  extends DialogFragment {

  override def onCreateDialog(savedInstanceState: Bundle): Dialog = {
    val mainView = getActivity.getLayoutInflater.inflate(R.layout.text_dialog, null)
    val textField: EditText = mainView.findViewById(R.id.name).asInstanceOf[EditText]
    val textInputLayout: TextInputLayout = mainView.findViewById(R.id.text_parent).
      asInstanceOf[TextInputLayout]
    textInputLayout.setHint(getActivity.getText(hintId))

    val builder: AlertDialog.Builder = new AlertDialog.Builder(getActivity)
    builder.setView(mainView)
      .setTitle(titleId)
      .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
        override def onClick(dialog: DialogInterface, id: Int) {
          val text = textField.getText.toString
          listener.onPositive(text)
        }
      })
      .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        override def onClick(dialog: DialogInterface, id: Int) {}
      })
    .create
  }
}
