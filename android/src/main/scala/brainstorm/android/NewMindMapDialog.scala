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

trait NewMindMapDialogListener {
  def onPositive(name: String): Unit
}

class NewMindMapDialog(listener: NewMindMapDialogListener) extends DialogFragment {

  override def onCreateDialog(savedInstanceState: Bundle): Dialog = {
    val inflater = getActivity.getLayoutInflater.inflate(R.layout.new_mindmap_dialog, null)

    val builder: AlertDialog.Builder = new AlertDialog.Builder(getActivity)
    builder.setView(inflater)
      .setTitle("Create MM")
      .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
        override def onClick(dialog: DialogInterface, id: Int) = {
          val text: String = inflater.findViewById(R.id.name).asInstanceOf[EditText].getText().toString()
          listener.onPositive(text)
        }
      })
    .create
  }

}
