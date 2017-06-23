package brainstorm.android

import java.io.File
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.widget.LinearLayout
import android.util.Log
import android.util.TypedValue 
///import android.content.res.Resources.Theme 

import scala.collection.mutable.Subscriber
import scala.collection.mutable.Publisher
import scala.collection.JavaConverters._

import jp.kai.forcelayout.Forcelayout

import brainstorm.core.MindMap


class MapDrawerFragment(mindMap: MindMap) extends Fragment 
with Subscriber[MindMap, Publisher[MindMap]] with View.OnLayoutChangeListener {

  lazy implicit val context: Context = getActivity
  lazy val ll: LinearLayout = getActivity.findViewById(R.id.rL).asInstanceOf[LinearLayout]
  var typedValue: TypedValue = new TypedValue();
  lazy val theme = context.getTheme();
  // @ColorInt
  lazy val nodeColor: Int = typedValue.data;

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View = {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.mapdrawer_fragment, parent, false)
  }

  override def onViewCreated(view: View, bundle: Bundle) {
    view.addOnLayoutChangeListener(this)
    theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
  }

  override def onLayoutChange(v: View, left: Int, top: Int, right: Int, 
    bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {

      val nodes = MapAdapter.getNodes(mindMap).toArray
      val edges = MapAdapter.getEdges(mindMap)
      Log.d("Number of nodes", nodes.length.toString)
      if (nodes.length > 0) {
        Log.d("Size in change", (right - left).toString ++ " " ++ (bottom - top).toString)
        val layout = new Forcelayout(context)
        layout.node().style(nodeColor)
        layout.`with`.friction(0.09)
          .distance(200).size(50)
          .gravity(0.04).nodes(nodes).links(edges).setDisplay(right-left, bottom-top).start()
          ll.addView(layout)
      }
  }

  override def notify(pub: Publisher[MindMap], mindMap: MindMap) {
    // redraw
    ll.removeAllViews
    val nodes = MapAdapter.getNodes(mindMap).toArray
    val edges = MapAdapter.getEdges(mindMap)
    if (nodes.length > 0) {
      Log.d("Size in notify", ll.getWidth.toString ++ " " ++ ll.getHeight.toString)
      val layout = new Forcelayout(context)
      layout.node().style(nodeColor)
      layout.`with`.friction(0.09)
        .distance(200000).size(50)
        .gravity(0.04).nodes(nodes).links(edges).setDisplay(ll.getWidth, ll.getHeight).start()
        ll.addView(layout)
    }
  }
}
