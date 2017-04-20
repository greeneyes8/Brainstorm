package brainstorm.core

import io.Source

object Parser {
  def parseFile(filename: String): MindMap = {
    val lines = Source.fromFile(filename).getLines

  }
  def parseText
  def parseLine
}
