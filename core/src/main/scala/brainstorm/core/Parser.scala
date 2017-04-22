package brainstorm.core

import io.Source

object Parser {
  def parseFile(filename: String): MindMap = {
    val lines = Source.fromFile(filename).getLines
    MindMap.fromText(lines, filename)
  }
  def parseText() = ""
  def parseLine() = ""
}
