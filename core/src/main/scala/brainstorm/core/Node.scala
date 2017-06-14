package brainstorm.core

import util.Try
import scala.collection.immutable.StringOps

/**
* A class to represent each Node.
* Specify its line and parent, which is a type of Option and then access 
* the these attributes like this:
* {{{
* val node = new Node("Hello", Option[None])
* node.line
* node.parent
* }}}
*
* @constructor Create a new node with `line` and `parent`.
* @param line The value of the node. Type: String
* @param parent The parent of the node. Type: Option[Node]
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
**/

class Node (var line: String, var parent: Option[Node]) extends DfsTraversable[Node] {
  var text = line
  if (parent isDefined) {
    parent.get.appendChild(this)
  }
  var children: Seq[Node] = Seq()
  /**
  * @return Adds a child to the node in given position.
  * @param node The node which is going to be added as a child.
  * @param position A position where this node is going to be added.
  **/
  def addChild(node: Node, position: Integer) = {
    children = (children.take(position) :+ node) ++ children.drop(position) 
  }
  /**
  * @return Adds a child to the node at the end of Seq.
  * @param node The node which is going to be added as a child.
  **/
  def appendChild(node: Node) = {
    children = children :+ node
  }
  /**
  * @return Removing a child from Seq which is the node given.
  * @param node The node which is going to be removed.
  **/
  def removeChild(node: Node) = {
    children = children.filter( _ != node)
  }

  override def getChildren() = children
  
  /**
  * @return Removing this node from parent.
  **/
  def remove(): Try[Unit] = Try(parent.get.removeChild(this))
  /**
  * @return Returs sequence of strings of lines from node's children and node itself.
  * @param separator Separator which gonna separate texts.
  **/
  def getText(separator: String): Seq[String] = {
    this.dfs2[String]((x) => x.text, (x) => x.++:(separator))
  }
  /**
  * @return Returning sequence of Nodes which are node's children and node itself.
  **/
  def getNodes(): Seq[Node] = {
    this.dfs[Node](x => x)
  }
  /**
  * @return Returs sequence of strings and Nodes from node's children and node itself.
  * @param separator Separator which gonna separate texts.
  **/
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
