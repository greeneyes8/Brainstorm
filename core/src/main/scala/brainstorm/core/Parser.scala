package brainstorm.core

import io.Source
import java.net.URI
import java.io.File


/**
* A case class used as an Exception when the syntax is incorrect.
*
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
case class WrongSyntax(line: Integer, cause: String) extends Exception {
  override def toString() = {
    super.toString() ++ "\n" ++ line.toString ++ ": " ++ cause
  }
}

/**
* A case class used as an Exception when the syntax is incorrect.
*
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
object Parser {
  def parseFile(filename: URI): MindMap = {
    val file = new File(filename)
    val lines = Source.fromFile(file).getLines.toSeq
    if (!lines.isEmpty) {
      val root = parseText(lines, None)
      new MindMap(file.getName, Some(root))
    } else {
      new MindMap(file.getName)
    }
  }
  def parseTextChecked(text: Seq[String], parent: Option[Node]): Node = {
    if (!text.isEmpty) {
      val rootIndent = text.head.prefixLength((c) => c == ' ')
      var considered: Seq[String] = text.tail
      val syntaxCheck: Option[(Int, Int)] = considered.map((s) => s.prefixLength(c => c == ' '))
        .zipWithIndex.asInstanceOf[Seq[(Int, Int)]].find(x => x._1 <= rootIndent)
      syntaxCheck match {
        case Some(x) => throw new WrongSyntax(x._2 + 1, "Only one root allowed")
        case None => parseText(text, parent)
      }
    } else {
      throw new WrongSyntax(0, "Write something")
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
