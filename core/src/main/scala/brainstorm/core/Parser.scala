package brainstorm.core

import io.Source

object Parser {
  def parse(filename: String): MindMap = {
    val lines = Source.fromFile(filename).getLines
    MindMap.fromText(lines, filename)
  }
}
