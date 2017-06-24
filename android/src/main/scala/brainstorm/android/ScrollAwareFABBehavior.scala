package brainstorm.android

import android.util.AttributeSet
import android.content.Context
import android.view.View
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton

class ScrollAwareFABBehavior(context: Context, attributeSet: AttributeSet)
  extends FloatingActionButton.Behavior(context, attributeSet) {

    override def onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
      button: FloatingActionButton, directTargetChild: View, target: View,
      nestedScrollAxes: Int): Boolean = {
        // Ensure we react to vertical scrolling
        nestedScrollAxes == View.SCROLL_AXIS_VERTICAL || 
          super.onStartNestedScroll(coordinatorLayout, button, directTargetChild,
          target, nestedScrollAxes)
    }

}
