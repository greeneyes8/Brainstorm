package brainstorm.core

import io.Source
import java.net.URI
import java.io.File


/**
* A case class used as an Exception when the syntax is incorrect.
*
* @param line Number of line where exception occured (as counted from 0)
* @param cause Cause of exception
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
case class WrongSyntax(val line: Integer, val cause: String) extends Exception {
  override def toString() = {
    super.toString() ++ "\n" ++ line.toString ++ ": " ++ cause
  }
}

/**
* A object used to parse files, lines and texts into a mindMap.
*
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
object Parser {
  /**
  * @return Parses a file into a mind map.
  * @param filename Name of a file where is the text to parse.
  **/
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
  /**
  * @return Parses a text into a mind map. This one throws an exception.
  * @param text A sequence of strings to parse.
  * @param parent A parent where we want to add the node.
  **/
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

  /**
  * @return Parses a text into a mind map.
  * @param text A sequence of strings to parse.
  * @param parent A parent where we want to add the node.
  **/
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

  /**
  * @return Parses a line into a node in a mind map
  * @param line A string to parse.
  * @param parent A parent where we want to add the node.
  **/
  def parseLine(line: String, parent: Option[Node]): Node = {
    val cutLine = line.dropWhile(x => x == ' ')
    new Node(cutLine, parent)
  }
}
