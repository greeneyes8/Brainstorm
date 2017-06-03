package brainstorm.android

import android.view.View
import android.widget.TextView
import android.content.Context

import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable

import brainstorm.core.MindMap
import brainstorm.core.Node

class NodeAdapter(implicit context: Context) {
  def convertNode(node: Node): View = {
    val os = new OvalShape()
    val sd = new ShapeDrawable(new OvalShape())
    val tv = new TextView(context)
    tv.setText(node.line)
    tv.setCompoundDrawables(null, null, null, sd)
    tv
  }
}
