package brainstorm.core

class MindMap (var name: String, val root: Node) {
  def this(name: String) = this(name, new Node(name, Nothing))
}
