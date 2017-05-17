package brainstorm.core

import io.Source

object Parser {
  def parseFile(filename: String): MindMap = {
    val lines = Source.fromFile(filename).getLines
    new MindMap(filename)
  }
  def parseText(text: Seq[String], parent: Option[Node]): Node = {
    var root:Node = parseLine(text(0), parent)
    var considered = text.tail
    if (!considered.isEmpty) {
      val indentation = considered.head.prefixLength(y => y == ' ')
      while (!considered.isEmpty) {
        val spanned = considered.tail.span((x) => x.prefixLength(y => y == ' ') > indentation)
        parseText(spanned._1.+:(considered.head), Some(root))
        considered = spanned._2
      }
    }
    root
  }
  def parseLine(line: String, parent: Option[Node]): Node = {
    val cutLine = line.dropWhile(x => x == ' ')
    new Node(cutLine, parent)
  }
}
