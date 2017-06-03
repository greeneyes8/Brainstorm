package brainstorm.android

import scala.math.Pi

import brainstorm.core.MindMap
import brainstorm.core.Node

class TreeLayouter(mindMap: MindMap) {
  def getNodesAndPositions(): Seq[(Node, (Float, Float))] = {
    val rootOption = mindMap.root
    // get max depth
    // for 
    rootOption match {
      case Some(root) => {

        val depths: Seq[(Int, Node)] = root.dfs2(x => (0, x),
          (y: (Int, Node)) => (y._1+1, y._2))
        val highest: Int = depths.unzip._1.max
        val sumFraction: Seq[(Node, Int, Double)] = depths.map(
          x => (x._2, x._1, if (x._2.getChildren.isEmpty) {1.0 * highest/x._1} else {0.0}))
        val allFractions: Seq[(Node, Int, Double)] = sumFraction.zipWithIndex
          .map(x => (x._1._1, x._1._2, x._1._3 + sumFraction.drop(x._2+1)
            .takeWhile(z => z._2 > x._1._2).unzip3._3.sum))
        val totalSum: Double = allFractions(0)._3
        val withDeg: Seq[(Node, Int, Double)] = allFractions.map(
          x => (x._1, x._2, allFractions
                            .takeWhile( y => y._1 ne x._1)
                            .filter(y => y._2 == x._2 || (y._2 < x._2 && y._1.getChildren.isEmpty))
                            .unzip3._3.sum / totalSum*2*Pi))

        withDeg.drop(1).map(x => (x._1, (x._2.toFloat, x._3.toFloat))).+:((root, (0.0f, 0.0f)))
      }
      case None => Seq()
    }
  }
}
