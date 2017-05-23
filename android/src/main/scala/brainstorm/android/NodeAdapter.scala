package brainstorm.android

import android.view.View
import android.widget.ImageView
import android.content.Context

import brainstorm.core.MindMap
import brainstorm.core.Node

class NodeAdapter(mindMap: MindMap)(implicit context: Context) {
  def convertWhole(): Seq[(View, Option[View])] = {
    val nodes: Seq[Node] = mindMap.getNodes
    val parents: Seq[Option[Node]] = nodes.map(_.parent)
    val views: Seq[View] = nodes.map(convertNode)
    val parentIndices: Seq[Option[Int]] = parents.map(
      x => x.map(p => nodes.indexOf(p)))
    val parentViews: Seq[Option[View]] = parentIndices.map(x => x.map(views(_)))
    views.zip(parentViews)
  }
  private def convertNode(node: Node): View = {
    new ImageView(context)
  }
  def convertOne(coordinates: Seq[Int]): View = {
    val node: Node = coordinates.foldLeft(mindMap.root.get)((x, i) => x.children(i))
    convertNode(node)
  }
}
