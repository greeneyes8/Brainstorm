package brainstorm.core

trait MindMapTextChangeListener {
  def textChange(start: Integer, oldLines: Seq[String], newLines: Seq[String])
}

trait MindMapChangeListener {
  def mindMapChange(newMindMap: MindMap)
}

class MindMapModel(val mindMap: MindMap) extends MindMapTextChangeListener {
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
  override def textChange(start: Integer, oldLines: Seq[String], newLines: Seq[String]) = {
    if (start != 0) {
      val highest: Integer = (oldLines ++ newLines).map((s) => s.prefixLength((c) => c == ' ')).max
      val lcapos: Integer = start - mindMap.getText.take(start).prefixLength((s) => s.prefixLength((c) => c == ' ') >= highest) + 1
      val lcaposheight: Integer = mindMap.getText()(lcapos).prefixLength((c) => c == ' ') 
      val text: Seq[String] = mindMap.getText.drop(lcapos).takeWhile((s) => s.prefixLength((c) => c == ' ') < lcaposheight)
      val node = mindMap.getNodes()(lcapos)
      node.remove
      Parser.parseText(text, node.parent)
    } else {
      val node = Parser.parseText(newLines, None)
      mindMap.root = Some(node)
    }

    // make change at lowest common ancestor
    // TODO: 1. Proposition
    //    find lowest common ancestor
    //    and parse newlines from there (parser.parseText)
    //    2. Proposition
    //    go line to line and using above functions change structure 
    //    note: propably faster the bigger the changes - one repin versus parsing whole tree anew
  }
}
