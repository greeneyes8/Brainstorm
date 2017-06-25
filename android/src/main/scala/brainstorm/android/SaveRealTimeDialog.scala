package brainstorm.android

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText

trait SaveRealTimeDialogListener {
  def onPositive(name: String): Unit
}

class SaveRealTimeDialog(listener: SaveRealTimeDialogListener) extends DialogFragment {
  implicit val context = this

  override def onCreateDialog(savedInstanceState: Bundle): Dialog = {
    val inflater = getActivity.getLayoutInflater.inflate(R.layout.savertmap_dialog, null)
    val confirm : String = context.getResources().getString(R.string.confirm)

    val builder: AlertDialog.Builder = new AlertDialog.Builder(getActivity)
    builder.setView(inflater)
      .setTitle(R.string.rtsave_t)
      .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
        override def onClick(dialog: DialogInterface, id: Int) {
          val text: String = inflater.findViewById(R.id.rt_name).asInstanceOf[EditText].getText().toString()
          listener.onPositive(text)
        }
      })
    .create
  }
}
