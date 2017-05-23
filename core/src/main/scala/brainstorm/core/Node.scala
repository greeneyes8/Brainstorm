package brainstorm.core

import util.Try
import scala.collection.immutable.StringOps

class Node (var line: String, var parent: Option[Node]) extends DfsTraversable[Node] {
  var text = line
  if (parent isDefined) {
    parent.get.appendChild(this)
  }
  var children: Seq[Node] = Seq()
  def addChild(node: Node, position: Integer) = {
    children = (children.take(position) :+ node) ++ children.drop(position) 
  }
  def appendChild(node: Node) = {
    children = children :+ node
  }
  def removeChild(node: Node) = {
    children = children.filter( _ != node)
  }

  override def getChildren() = children
  
  def remove(): Try[Unit] = Try(parent.get.removeChild(this))
  def getText(separator: String): Seq[String] = {
    this.dfs2[String]((x) => x.text, (x) => x.++:(separator))
  }
  def getNodes(): Seq[Node] = {
    this.dfs[Node](x => x)
  }
  def getTextWithNodes(separator: String): Seq[(Node, String)] = {
    getNodes.zip(this.getText(separator))
  }
  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[Node]) {
      val check: Node = obj.asInstanceOf[Node]
      check.text == text && check.children.sameElements(children)
    } else {
      false
    }
  }
}
