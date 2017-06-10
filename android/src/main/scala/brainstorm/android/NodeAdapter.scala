package brainstorm.android

import android.view.View
import android.widget.TextView
import android.content.Context

import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable

import java.util.ArrayList

import brainstorm.core.MindMap
import brainstorm.core.Node

import jp.kai.forcelayout.Links
import jp.kai.forcelayout.Nodes

object NodeAdapter {
  def convertNode(node: Node)(implicit context: Context): Nodes.NodePair = {
  }
  def getEdge(node: Node)(implicit context: Context): Option[Links.LinkPair] = {
    node.parent.map()
  }
}

object MapAdapter {
  def getEdges(mindMap: MindMap)(implicit context: Context): Links = {
    new ArrayList(mindMap.getNodes.map(NodeAdapter.getEdge(_)))
  }
  def getNodes(mindMap: MindMap)(implicit context: Context): Nodes = {
    val nodePairs = mindMap.getNodes.map(NodeAdapter.convertNode(_))
    new ArrayList(nodePairs)
  }
  def getNodesAndEdges(mindMap: MindMap)(implicit context: Context): (Nodes, Links) = {
    (getNodes(mindMap), getEdges(mindMap))
  }

}
