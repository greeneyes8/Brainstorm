package brainstorm.android

import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.RecyclerView
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView 

class SwipeToDeleteHelper(adapter: SwipeAdapter) extends ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
  override def onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder): Boolean = false

  override def onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
    val position: Int = viewHolder.getAdapterPosition
    adapter.remove(position)
  }

  override def getSwipeDirs(recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder): Int = {
    val position: Int = viewHolder.getAdapterPosition
    val swipeable: SwipeAdapter = recyclerView.getAdapter.asInstanceOf[SwipeAdapter]
    // If is swiped you cant swipe further
    if (swipeable.isUndoOn && swipeable.isPendingRemoval(position)) {
      0
    } else {
      super.getSwipeDirs(recyclerView, viewHolder)
    }
  }
  override def onChildDraw(canvas: Canvas, recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder, dx: Float, dy: Float,
    actionState: Int, isActive: Boolean) {
  }
}

trait SwipeAdapter {
  def isUndoOn(): Boolean
  def isPendingRemoval(position: Int): Boolean
  def pendingRemoval(position: Int)
  def remove(position: Int): Unit
}

class rr extends RecyclerView.ItemDecoration {
  override def onDraw(canvas: Canvas, recyclerView: RecyclerView,
    recyclerState: RecyclerView.State) {
    super.onDraw(canvas, recyclerView, recyclerState)
  }
}
