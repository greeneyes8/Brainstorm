package brainstorm.core

case class RootDeletionException extends Exception {

}

class Node (var text: String, var parent: Option[Node]) {
  val children: collection.mutable.Set[Node] = collection.mutable.Set()
  def remove(): Unit = {
    parent match {
      case Some(parent) => parent.children -= this
      case None => throw new RootDeletionException()
    }
  }
}
