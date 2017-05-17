package brainstorm.android

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import android.app.Fragment 
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.Toast

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
        val wasClicked: String = " " ++ context.getResources().getString(R.string.wasClicked)
        val intent = new Intent(context, new MapActivity(file))
        context.startActivity(intent)
        
        Toast.makeText(v.getContext, file.getName ++ wasClicked, 0).show
      }
    })
    holder.clickable.setOnLongClickListener(new View.OnLongClickListener() {
      override def onLongClick(v: View): Boolean = {
        val wasDeleted : String = " " ++ context.getResources().getString(R.string.wasDeleted)
        Toast.makeText(v.getContext, file.getName ++ wasDeleted, 0).show
        val result = file.delete
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
