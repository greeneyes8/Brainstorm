package brainstorm.core

import scala.util.Try
import scala.collection.mutable.Subscriber
import scala.collection.mutable.Publisher

/**
* A class used to handle notifing when the mind map is changed.
*
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/

class MindMapModel(val mindMap: MindMap) extends 
  Publisher[MindMap] with Subscriber[Seq[String], Publisher[Seq[String]]] {
  override def notify(publisher: Publisher[Seq[String]], newLines: Seq[String]) = {
    val root: Node = Parser.parseTextChecked(newLines, None)
    mindMap.root = Some(root)
    publish(mindMap)
  }
}
