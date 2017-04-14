package brainstorm.core

import scala.util.Try

class Node (var text: String, var parent: Option[Node]) {
  val children: collection.mutable.Set[Node] = collection.mutable.Set()
  def remove(): Try[Unit] = Try(parent.get.children.-=(this))
  
}
