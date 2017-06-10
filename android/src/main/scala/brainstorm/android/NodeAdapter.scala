package brainstorm.android

import scala.collection.JavaConverters._

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
    null
  }
  def getEdge(node: Node)(implicit context: Context): Option[Links.LinkPair] = {
    // node.parent.map()
    None
  }
}

object MapAdapter {
  def getEdges(mindMap: MindMap)(implicit context: Context): Links = {
    val edges =  mindMap.getNodes.map(NodeAdapter.getEdge(_))
    val links = new Links
    edges.filter(_.isDefined).map(_.get).foreach(links.add(_))
    links
  }
  def getNodes(mindMap: MindMap)(implicit context: Context): Nodes = {
    val nodePairs: Seq[Nodes.NodePair] = mindMap.getNodes.map(NodeAdapter.convertNode(_))
    val nodes = new Nodes
    nodePairs.foreach(nodes.add(_))
    nodes
  }
  def getNodesAndEdges(mindMap: MindMap)(implicit context: Context): (Nodes, Links) = {
    (getNodes(mindMap), getEdges(mindMap))
  }

}
