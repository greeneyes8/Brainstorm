package brainstorm.core

abstract class MindMapEvent {
  def apply(mindMap: MindMap)
  def reverse(): MindMapEvent
}

case class SerialEvent(events: Seq[MindMapEvent]) extends MindMapEvent {
  override def apply(mindMap: MindMap) = {
    events.foreach(x => x(mindMap))
  }
  override def reverse() =
    new SerialEvent(events.reverse.map(x => x.reverse))
  def undo(mindMap: MindMap): SerialEvent = {
    events.last.reverse()(mindMap)
    new SerialEvent(events.init)
  }
}

case class AddNodeEvent(parent: Option[Node], node: Node) extends MindMapEvent {
  override def apply(mindMap: MindMap) = {
    parent match {
      case Some(p) => p.appendChild(node)
      case None => { mindMap.root = Some(node) }
    }
    node.parent = parent
  }
  override def reverse() =
    new RemoveNodeEvent(node)
}

case class RemoveNodeEvent(node: Node) extends MindMapEvent {
  override def apply(mindMap: MindMap) = {
    node.remove
  }
  override def reverse() = {this}
    //new RemoveNodeEvent(node)
}

case class PutNodeEvent(parent: Option[Node], node: Node, position: Int) extends MindMapEvent {
  override def apply(mindMap: MindMap) = {
    parent.get.addChild(node, position)
    node.parent = parent
  }
  override def reverse() =
    new RemoveNodeEvent(node)
}

object RepinNodeEvent {
  def apply(parent: Option[Node], node: Node, position: Int) = 
    SerialEvent(Seq(RemoveNodeEvent(node), PutNodeEvent(parent, node, position)))
  def unapply(rne: SerialEvent) = SerialEvent.unapply(rne.asInstanceOf[SerialEvent])
}
