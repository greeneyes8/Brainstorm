package brainstorm.core

class MindMap (var name: String, var root: Option[Node]) {
  def this(name: String) = this(name, None)
  
  def getText(): Seq[String] = {
    root.map((x) => x.getText).getOrElse(Seq())
  }
  def getNodes(): Seq[Node] = {
    root.map((x) => x.getNodes).getOrElse(Seq())
  }
  def getTextWithNodes(): Seq[(Node, String)] = {
    root.map((x) => x.getTextWithNodes).getOrElse(Seq())
  }
}

//object MindMap {
//  def fromText(text: Iterator[String], name: String) = {
//    if (text.hasNext) {
//      new MindMap(name, Node.fromText(text, None))
//    } else {
//      new MindMap(name)
//    }
//  }
//}
