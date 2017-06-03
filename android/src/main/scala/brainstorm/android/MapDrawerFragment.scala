package brainstorm.android

import java.io.File
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context

import scala.collection.mutable.Subscriber
import scala.collection.mutable.Publisher

import brainstorm.core.MindMap


class MapDrawerFragment(mindMap: MindMap) extends Fragment with Subscriber[MindMap, Publisher[MindMap]] {
  lazy val treeRelativeLayout: TreeRelativeLayout = 
    getActivity.findViewById(R.id.rL).asInstanceOf[TreeRelativeLayout]

  lazy implicit val context: Context = getActivity
  var coordinator: Coordinator = _
  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View = {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.mapdrawer_fragment, parent, false)
  }

  override def onViewCreated(view : View, savedInstanceState : Bundle) {
    val treeLayouter = new TreeLayouter(mindMap)
    coordinator = new Coordinator(treeLayouter)
    val viewsAndPositions = coordinator.getViewsAndPositions()
    val vAndP = viewsAndPositions.map(x => (x._1, x._2, (x._3._1 * 100, x._3._2)))
    treeRelativeLayout.paintViews(getActivity, vAndP)
  }

  override def notify(pub: Publisher[MindMap], mindMap: MindMap) {
    // redraw
    val viewsAndPositions = coordinator.getViewsAndPositions()
    val vAndP = viewsAndPositions.map(x => (x._1, x._2, (x._3._1 * 1, x._3._2)))
    treeRelativeLayout.paintViews(getActivity, vAndP)
  }
}
