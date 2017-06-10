package brainstorm.android

import android.content.Context
import android.view.View

import brainstorm.core.Node

class Coordinator(layouter: TreeLayouter)(implicit context: Context) {
//  val nodeAdapter = new NodeAdapter()
//  def getViewsAndPositions(): Seq[(View, Option[View], (Float, Float))] = {
//    val nodesAndPos = layouter.getNodesAndPositions()
//    val nodes = nodesAndPos.unzip._1
//    val parents: Seq[Option[Node]] = nodes.map(x => x.parent)
//    val parentInd: Seq[Option[Int]] = parents.map(x => x.map(y => nodes.indexOf(y)))
//    val positions = nodesAndPos.unzip._2
//    
//    val views = nodes.map(x => nodeAdapter.convertNode(x))
//    val parentViews = parentInd.map(_.map(y => views(y)))
//    views.zip(parentViews).zip(positions) map { case ((v, pv), p) => (v, pv, p)}
//  }
//
}
