package brainstorm.core

import util.Try

class Node (var text: String, var parent: Option[Node]) {
  val children: collection.mutable.LinkedHashSet[Node] = collection.mutable.LinkedHashSet()
  def remove(): Try[Unit] = Try(parent.get.children.-=(this))
  def addChild(chld: Node): Unit = {
    children += chld
    chld.parent = Some(this)
  }
}

// To jest głupie TODO:
object Node {
  def fromText(lines: Iterator[String], parent: Option[Node]): Node = {
    val text = lines.next
    val node = new Node(text, parent) 
    if (lines.hasNext) {
      val indentation = lines.buffered.head.prefixLength((x) => x == ' ' || x == '\t')
      var next_lines = lines
      while (next_lines.hasNext) {
        val (prefix, rest) = next_lines.span((l) => l.prefixLength((x) => x == ' ' || x == '\t') > indentation) 
        node.children += Node.fromText(prefix, Some(node))
        next_lines = rest
      }
    }
    node
  }
}
