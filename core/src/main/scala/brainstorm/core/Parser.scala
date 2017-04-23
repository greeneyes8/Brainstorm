package brainstorm.core

import io.Source

object Parser {
  def parseFile(filename: String): MindMap = {
    val lines = Source.fromFile(filename).getLines
    MindMap.fromText(lines, filename)
  }
  def parseText(text: Seq[String]): Node = {
    for (line <- text){
      parseLine(line, None)
    }
    new Node("lala", None)
  }
  def parseLine(line: String, parent: Option[Node]): Node = 
    new Node(line, parent)
}
