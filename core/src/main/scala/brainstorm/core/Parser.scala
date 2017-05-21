package brainstorm.core

import io.Source
import java.net.URI

case class WrongSyntax(line: Integer, cause: String) extends Exception
object Parser {
  def parseFile(filename: URI): MindMap = {
    val lines = Source.fromFile(filename).getLines.toSeq
    if (!lines.isEmpty) {
      val root = parseText(lines, None)
      new MindMap(filename.toString, Some(root))
    } else {
      new MindMap(filename.toString)
    }
  }
  def parseTextChecked(text: Seq[String], parent: Option[Node]): Node = {
    if (!text.isEmpty) {
      val rootIndent = text.head.prefixLength((c) => c == ' ')
      var considered = text.tail
      val syntaxCheck:Option[(Int, Int)] = considered.map((s) => s.prefixLength(c => c == ' ')).zipWithIndex.find(x => x._1 <= rootIndent)
      syntaxCheck match {
        case Some(x) => throw new WrongSyntax(x._1 + 1, "Incorrect indentation")
        case None => parseText(text, parent)
      }
    } else {
      throw new WrongSyntax(0, "WriteSth")
    }
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
