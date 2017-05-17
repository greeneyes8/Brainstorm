package brainstorm.core

import util.Try
import scala.collection.immutable.StringOps

class Node (var line: String, var parent: Option[Node]) {
  var text = line
  if (parent isDefined) {
    parent.get.children += this
  }
  val children: collection.mutable.LinkedHashSet[Node] = collection.mutable.LinkedHashSet()
  def remove(): Try[Unit] = Try(parent.get.children.-=(this))
  def getText(): Seq[String] = {
    val seq: Seq[Node] = children.toSeq
    val seq2: Seq[String] = seq.flatMap((x) => x.getText)
    val seq3: Seq[String] = seq2.map(x =>x.++:("  "))
    seq3.+:(text)
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

//object Node {
//  def fromText(lines: Iterator[String], parent: Option[Node]): Node = {
//    val text = lines.next
//    val node = new Node(text, parent) 
//    if (lines.hasNext) {
//      val indentation = lines.buffered.head.prefixLength((x) => x == ' ' || x == '\t')
//      var next_lines = lines
//      while (next_lines.hasNext) {
//        val (prefix, rest) = next_lines.span((l) => l.prefixLength((x) => x == ' ' || x == '\t') > indentation) 
//        node.children += Node.fromText(prefix, Some(node))
//        next_lines = rest
//      }
//    }
//    node
//  }
//}
