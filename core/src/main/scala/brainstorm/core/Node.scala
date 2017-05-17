package brainstorm.core

import util.Try
import scala.collection.immutable.StringOps

class Node (var line: String, var parent: Option[Node]) extends DfsTraversable[Node] {
  var text = line
  if (parent isDefined) {
    parent.get.children += this
  }
  val children: collection.mutable.LinkedHashSet[Node] = collection.mutable.LinkedHashSet()
  def remove(): Try[Unit] = Try(parent.get.children.-=(this))
  override def getChildren() = children.toSeq
  def getText(): Seq[String] = {
    this.dfs2[String]((x) => x.text, (x) => x.++:("  "))
  }
  def getNodes(): Seq[Node] = {
    this.dfs[Node](x => x)
  }
  def getTextWithNodes(): Seq[(Node, String)] = {
    getNodes.zip(this.getText)
  }
  override def equals(obj: Any): Boolean = {
    // god how much cleaner it would be with monad
    if (obj.isInstanceOf[Node]) {
      val check: Node = obj.asInstanceOf[Node]
      check.text == text && check.children.sameElements(children)
    } else {
      false
    }
  }
}
