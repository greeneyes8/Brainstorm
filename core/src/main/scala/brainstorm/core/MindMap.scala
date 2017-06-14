package brainstorm.core

/**
* A class to represent a mind map.
* Specify its name and root, which is a type of Option and then access 
* the these attributes like this:
* {{{
* val mindMap = new MindMap("Mind Map", new Node("Hello", Option[None]))
* mindMap.name
* mindMap.root
* }}}
*
* @constructor Create a new mind map with `name` and `root`.
* @param name A name of the mind map. Type: String
* @param root A root of the mind map. Type: Option[Node]
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
*/

class MindMap (var name: String, var root: Option[Node] = None) {
  /**
   * @constructor An auxiliary constructor which needs only a name to create a mind map.
   * @param name A name of the mind map. Type: String
   */
  @deprecated
  def this(name: String) = this(name, None)
  
  /**
   * @return Returns a sequence of lines of every node in mind map.
   * @param separator A separator which separates the lines.
   */
  def getText(separator: String): Seq[String] = {
    root.map((x) => x.getText(separator)).getOrElse(Seq())
  }

  /**
   * @return Returns a sequence of nodes in mind map.
   */
  def getNodes(): Seq[Node] = {
    root.map((x) => x.getNodes).getOrElse(Seq())
  }

  /**
   * @return Returns a sequence of nodes and line from mind map.
   * @param separator A separator which separates the lines.
   */
  def getTextWithNodes(separator: String): Seq[(Node, String)] = {
    root.map((x) => x.getTextWithNodes(separator)).getOrElse(Seq())
  }
}
