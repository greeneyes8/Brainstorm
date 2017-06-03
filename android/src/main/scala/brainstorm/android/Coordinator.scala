package brainstorm.android

import android.content.Context

class Coordinator(layouter: TreeLayouter)(implicit context: Context) {
  val nodeAdapter = new NodeAdapter()
  def getViewsAndPositions() = {
    val nodesAndPos = layouter.getNodesAndPositions()
    val nodes = nodesAndPos.unzip._1
    val parents = nodes.map(x => x.parent)
    val parentInd = parents.map(x => nodes.indexOf(x))
    val positions = nodesAndPos.unzip._2
    
    val views = nodes.map(x => nodeAdapter.convertNode(x))
    val parentViews = parentInd.map(x => views(x))
    views.zip(parentViews).zip(positions)
  }

}
