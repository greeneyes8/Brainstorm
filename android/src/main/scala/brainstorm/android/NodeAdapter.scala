package brainstorm.android

import android.content.Context

import brainstorm.core.MindMap
import brainstorm.core.Node

import jp.kai.forcelayout.Links
import jp.kai.forcelayout.Nodes

object NodeAdapter {
  def convertNode(node: Node)(implicit context: Context): String =
    node.line
  
  def getEdge(node: Node)(implicit context: Context): Option[(String, String)] = 
    node.parent.map(x => (x.line, node.line))
}

object MapAdapter {
  def getEdges(mindMap: MindMap)(implicit context: Context): Links = {
    val edges =  mindMap.getNodes.map(NodeAdapter.getEdge(_))
    val links = new Links
    edges.filter(_.isDefined).map(_.get).map(x => new Links.LinkPair(x._1, x._2))
      .foreach(x => links.add(x))
    links
  }

  def getNodes(mindMap: MindMap)(implicit context: Context): Seq[String] =
    mindMap.getNodes.map(NodeAdapter.convertNode(_))

  def getNodesAndEdges(mindMap: MindMap)(implicit context: Context): (Seq[String], Links) =
    (getNodes(mindMap), getEdges(mindMap))
}
