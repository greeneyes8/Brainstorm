package brainstorm.core

/**
* A trait to splitting the children by given function.
* IMPORTANT: If you want to DfsTraversable be generic, the type has to extends this trait. 
*
* @version 1.0
* @see See [[https://github.com/kd226/Brainstorm/]] for more information.
**/

trait DfsTraversable[A <: DfsTraversable[A]] {
  /**
  * @return Returns the sequence of children.
  **/
  def getChildren(): Seq[A]
  /**
  * @return Apply the function to every child.
  * @param func A function which is going to be applied.
  **/
  def dfs[B](func: A => B): Seq[B] = {
    val seq: Seq[A] = getChildren()
    val seq2: Seq[B] = seq.flatMap(x => x.dfs(func))
    seq2.+:(func(this.asInstanceOf[A]))
  }
  /**
  * @return Apply the function to every child and everyLevel apply another function.
  * @param func A function which is going to be applied.
  * @param levelFun A function which is going to be applied every level.
  **/
  def dfs2[B](func: A => B, levelFun: B => B): Seq[B] = {
    val seq: Seq[A] = getChildren()
    val seq2: Seq[B] = seq.flatMap(x => x.dfs2(func, levelFun))
    val seq3: Seq[B] = seq2.map(levelFun)
    seq3.+:(func(this.asInstanceOf[A]))
  }
}
