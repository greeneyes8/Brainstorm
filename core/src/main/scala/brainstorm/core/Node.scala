package brainstorm.core

case class RootDeletion extends Exception {

}

class Node (var text: String, var parent: Option[Node]) {
  val children = Set()
  def remove() = {
    parent match {
      case Some(parent) => parent.children -= this
      case None => throw new RootDeletionException()
    }
  }
}
