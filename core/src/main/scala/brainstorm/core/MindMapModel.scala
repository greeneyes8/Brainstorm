package brainstorm.core

//import scala.reflect.runtime.universe._
import scala.util.Try
import scala.collection.mutable.Subscriber
import scala.collection.mutable.Publisher


class MindMapModel(val mindMap: MindMap) extends 
  Publisher[MindMap] with Subscriber[Seq[String], Publisher[Seq[String]]] {
  override def notify(publisher: Publisher[Seq[String]], newLines: Seq[String]) = {
    val root: Node = Parser.parseTextChecked(newLines, None)
    mindMap.root = Some(root)
    publish(mindMap)
    // make change at lowest common ancestor
    // TODO: 1. Proposition
    //    find lowest common ancestor
    //    and parse newlines from there (parser.parseText)
    //    2. Proposition
    //    go line to line and using above functions change structure 
    //    note: propably faster the bigger the changes - one repin versus parsing whole tree anew
  }
}
