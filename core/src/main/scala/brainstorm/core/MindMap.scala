package brainstorm.core

class MindMap private (var name: String,val root: Node) {
  def this(name: String) = this(name, new Node(name, None))
  def getText() = ""
}

object MindMap {
  def fromText(text: Seq[String], name: String) = {
    if (text.hasNext) {
      new MindMap(name, Node.fromText(text, None))
    } else {
      new MindMap(name)
    }
  }
}
