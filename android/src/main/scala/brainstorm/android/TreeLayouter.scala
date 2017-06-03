package brainstorm.android

import brainstorm.core.MindMap
import brainstorm.core.Node

class TreeLayouter(mindMap: MindMap) {
  def getNodesAndPositions(): Seq[(Node, (Float, Float))] = {
    throw new NotImplementedError()
  }
}
