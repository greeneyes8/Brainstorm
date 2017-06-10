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

import scala.collection.mutable.Subscriber
import scala.collection.mutable.Publisher
import scala.collection.JavaConverters._

import jp.kai.forcelayout.Forcelayout

import brainstorm.core.MindMap


class MapDrawerFragment(mindMap: MindMap) extends Fragment with Subscriber[MindMap, Publisher[MindMap]] {

  lazy implicit val context: Context = getActivity
  lazy val ll: LinearLayout = getActivity.findViewById(R.id.rL).asInstanceOf[LinearLayout]

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View = {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.mapdrawer_fragment, parent, false)
  }

  override def onViewCreated(view : View, savedInstanceState : Bundle) {
    val layout = new Forcelayout(context)
    val nodes = MapAdapter.getNodes(mindMap).toArray
    val edges = MapAdapter.getEdges(mindMap)
    Log.d("SS", nodes(1))
    layout.`with`.friction(0.09)
      .distance(200).size(50)
      .gravity(0.14).nodes(nodes).links(edges).start()
    ll.addView(layout)

  }

  override def notify(pub: Publisher[MindMap], mindMap: MindMap) {
    // redraw
    val layout = new Forcelayout(context)
    val nodes = MapAdapter.getNodes(mindMap).toArray
    val edges = MapAdapter.getEdges(mindMap)
    layout.`with`().nodes(nodes).links(edges)
    ll.addView(layout)
  }
}
