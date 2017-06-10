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

import jp.kai.forcelayout.Forcelayout

import brainstorm.core.MindMap


class MapDrawerFragment(mindMap: MindMap) extends Fragment with Subscriber[MindMap, Publisher[MindMap]] {

  lazy implicit val context: Context = getActivity
  lazy val layout: Forcelayout = context.findViewById(R.id.rL).asInstanceOf[Forcelayout]

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
    savedInstanceState: Bundle): View = {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.mapdrawer_fragment, parent, false)
  }

  override def onViewCreated(view : View, savedInstanceState : Bundle) {

  }

  override def notify(pub: Publisher[MindMap], mindMap: MindMap) {
    // redraw
  }
}
