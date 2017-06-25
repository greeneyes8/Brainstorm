package brainstorm.core

/**
* An abstract class used to handle mind map events.
* UNUSED YET!
*
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
abstract class MindMapEvent {
  /**
   * @return Used to apply an event to a mind map.
   * @param mindMap A mind to which this event will be applied.
   */
  def apply(mindMap: MindMap): Unit
  /**
   * @return Reverse the action which is done by the event.
   */
  def reverse(): MindMapEvent
}

/**
* A case class used to handle serial mind map events.
* UNUSED YET!
*
* @param events A sequence of events which will be handling together.
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
case class SerialEvent(events: Seq[MindMapEvent]) extends MindMapEvent {
  override def apply(mindMap: MindMap) =
    events.foreach(_(mindMap))
  
  override def reverse() =
    new SerialEvent(events.reverse.map(_.reverse))

  def undo(mindMap: MindMap): SerialEvent = {
    events.last.reverse()(mindMap)
    new SerialEvent(events.init)
  }
}

/**
* A case class used to handle addNode mind map events.
* UNUSED YET!
*
* @param parent A parent of the node. Type: Option[Node].
* @param node A node which are adding with an event.
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
case class AddNodeEvent(parent: Option[Node], node: Node) extends MindMapEvent {
  override def apply(mindMap: MindMap) {
    parent match {
      case Some(p) => p.appendChild(node)
      case None => mindMap.root = Some(node)
    }
    node.parent = parent
  }

  override def reverse() = new RemoveNodeEvent(node)
}

/**
* A case class used to handle removeNode mind map events.
* UNUSED YET!
*
* @param node A node which will be removed by the event.
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
case class RemoveNodeEvent(node: Node) extends MindMapEvent {
  override def apply(mindMap: MindMap) =
    node.remove
  
  override def reverse() = this
}

/**
* A case class used to handle putNode mind map events.
* UNUSED YET!
*
* @param parent A parent of the node. Type: Option[Node].
* @param node A node which will be put in given position.
* @param position A position where the node will be put.
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/
case class PutNodeEvent(parent: Option[Node], node: Node, position: Int) extends MindMapEvent {
  override def apply(mindMap: MindMap) = {
    parent.get.addChild(node, position)
    node.parent = parent
  }

  override def reverse() = new RemoveNodeEvent(node)
}
