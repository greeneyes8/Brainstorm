package brainstorm.android

import scala.collection.mutable.Subscriber
import scala.collection.mutable.Publisher
import scala.collection.JavaConverters._

import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.widget.LinearLayout
import android.util.Log
import android.util.TypedValue 

import jp.kai.forcelayout.Forcelayout

import brainstorm.core.MindMap


class MapDrawerFragment(mindMap: MindMap) extends Fragment 
with Subscriber[MindMap, Publisher[MindMap]] with View.OnLayoutChangeListener {

  lazy implicit val context: Context = getActivity
  lazy val ll: LinearLayout = getActivity.findViewById(R.id.rL).asInstanceOf[LinearLayout]

  val typedValue: TypedValue = new TypedValue
  lazy val theme = context.getTheme

  lazy val nodeColor: Int = typedValue.data

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View =
    inflater.inflate(R.layout.mapdrawer_fragment, parent, false)


  override def onViewCreated(view: View, bundle: Bundle) {
    view.addOnLayoutChangeListener(this)
    theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
  }

  def drawGraph(width: Int, height: Int) {
    val nodes = MapAdapter.getNodes(mindMap).toArray
    val edges = MapAdapter.getEdges(mindMap)
    Log.d("Number of nodes", nodes.length.toString)
    if (nodes.length > 0) {
      Log.d("Size in change", width.toString ++ " " ++ height.toString)
      val layout = new Forcelayout(context)
      layout.node().style(nodeColor)
      layout.`with`.friction(0.09).distance(200).size(50).gravity(0.04)
        .nodes(nodes).links(edges).setDisplay(width, height).start()
      ll.addView(layout)
    }
  }

  override def onLayoutChange(v: View, left: Int, top: Int, right: Int, 
    bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
    drawGraph(right-left, bottom-top)
  }

  override def notify(pub: Publisher[MindMap], mindMap: MindMap) {
    // redraw
    ll.removeAllViews
    drawGraph(ll.getWidth, ll.getHeight)
  }
}
