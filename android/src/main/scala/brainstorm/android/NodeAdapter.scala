package brainstorm.android

import android.view.View
import android.widget.ImageView
import android.content.Context

import brainstorm.core.MindMap
import brainstorm.core.Node

class NodeAdapter(implicit context: Context) {
  def convertNode(node: Node): View = {
    new ImageView(context)
  }
}
