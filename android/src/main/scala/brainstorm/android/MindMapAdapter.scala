package brainstorm.android

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater

class MindMapAdapter (stringi: Array[String]) extends RecyclerView.Adapter[ViewHolder] {

  override def onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = {
        val v: View = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mindmap_list_row, parent, false);
        new ViewHolder(v);
  }

  override def onBindViewHolder(holder: ViewHolder, position: Int) = {
    val file = stringi(position)
    holder.filename.setText(file)
  }

  override def getItemCount(): Int = stringi.size
}

class ViewHolder(var v: View) extends RecyclerView.ViewHolder(v) {
  val filename: TextView = v.findViewById(R.id.filename).asInstanceOf[TextView]
}
