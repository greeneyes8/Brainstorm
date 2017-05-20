package brainstorm.core

trait DfsTraversable[A <: DfsTraversable[A]] {
  def getChildren(): Seq[A]
  def dfs[B](func: A => B): Seq[B] = {
    val seq: Seq[A] = getChildren()
    val seq2: Seq[B] = seq.flatMap(x => x.dfs(func))
    seq2.+:(func(this.asInstanceOf[A]))
  }
  def dfs2[B](func: A => B, levelFun: B => B): Seq[B] = {
    val seq: Seq[A] = getChildren()
    val seq2: Seq[B] = seq.flatMap(x => x.dfs2(func, levelFun))
    val seq3: Seq[B] = seq2.map(levelFun)
    seq3.+:(func(this.asInstanceOf[A]))
  }
}
