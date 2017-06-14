package brainstorm.android

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import android.app.Fragment 
import android.support.v7.widget.RecyclerView
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.Toast
import android.app.AlertDialog

class MindMapAdapter (root: File, context:Fragment) extends RecyclerView.Adapter[ViewHolder] {
  var files: Array[File] = root.listFiles

  override def onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = {
        val v: View = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mindmap_list_row, parent, false);
        new ViewHolder(v);
  }

  override def onBindViewHolder(holder: ViewHolder, position: Int) = {
    val file = files(position)
    holder.filename.setText(file.getName)
    val format = new SimpleDateFormat("dd-MM-yyyy HH:mm")
    val date = new Date(file.lastModified)
    holder.moddate.setText(format.format(date))
    holder.v.setOnClickListener(new View.OnClickListener() {
      override def onClick(v: View) = {

        val intent = new Intent(context.getActivity, classOf[MapActivity]).putExtra("file", file.toURI())
        context.startActivity(intent)
      }
    })
    holder.clickable.setOnLongClickListener(new View.OnLongClickListener() {
      var result : Boolean = _
      override def onLongClick(v: View): Boolean = {
        val alertDialogBuilder : AlertDialog.Builder = new AlertDialog.Builder(context.getActivity())
         alertDialogBuilder
            .setMessage("Do you want to delete file: " + file.getName + "?")
            .setCancelable(true)
            .setPositiveButton("Yes.",new DialogInterface.OnClickListener() {
                            override def onClick(dialog : DialogInterface,id : Int) {
                                result = file.delete
                            }
                        })
            .setNegativeButton("No.",new DialogInterface.OnClickListener() {
                            override def onClick(dialog : DialogInterface,id : Int) {
                                //
                            }
            })

        val alertDialog : AlertDialog = alertDialogBuilder.create();
        alertDialog.show();
        invalidate
        result
      }
    })
  }

  override def getItemCount(): Int = files.size

  def invalidate(): Unit = {
    files = root.listFiles
    notifyDataSetChanged()
  }
}

class ViewHolder(var v: View) extends RecyclerView.ViewHolder(v) {
  val filename: TextView = v.findViewById(R.id.filename).asInstanceOf[TextView]
  val moddate: TextView = v.findViewById(R.id.moddate).asInstanceOf[TextView]
  val clickable: View = v.findViewById(R.id.whole)
}
