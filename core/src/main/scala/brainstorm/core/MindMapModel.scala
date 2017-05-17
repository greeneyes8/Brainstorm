package brainstorm.core

class MindMapModel(val mindMap: MindMap) {
  def deleteNode(node: Node) = {
    node.remove
  }
  def repinNode(node: Node, newParent: Option[Node]) = {
    node.remove
    node.parent = newParent
  }
  def addNode(node: Node, parent: Option[Node]) = {
    node.parent = parent
    parent match {
      case Some(parentNode) => parentNode.children += node
      case None => mindMap.root = Some(node)
    }
  }
  def textChange(start: Integer, oldLines: Seq[String], newLines: Seq[String]) = {

  }
}
