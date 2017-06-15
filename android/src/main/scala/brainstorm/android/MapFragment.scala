package brainstorm.android

import scala.util.Try
import scala.util.Success
import scala.util.Failure
import scala.collection.mutable.Publisher

import java.io.File
import android.app.Fragment 
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.text.TextWatcher
import android.util.Log
import android.text.Editable
import android.text.Spanned 
import android.graphics.Color 
import android.text.style.BackgroundColorSpan 

import brainstorm.core.MindMapModel
import brainstorm.core.MindMap
import brainstorm.core.WrongSyntax

class AndroidTextWatcher extends Publisher[Seq[String]] with TextWatcher {
  var newLines: Seq[String] = _
  var processed: Boolean = true
  val errorSpan = new BackgroundColorSpan(Color.RED)

  override def afterTextChanged(s: Editable) = {
    Log.d("Args", newLines.zipWithIndex.toString)
    val parseTry = Try(publish(newLines))
    parseTry match {
      case Success(_) => {
        processed = true
        s.removeSpan(errorSpan)
      }
      case Failure(e) => {
        if (processed) {
          processed = false
        } 
        e match {
          case WrongSyntax(line, cause) => {

            // TODO: Highlight the line that's causing problem
            val lines = s.toString.split('\n').toSeq
            val lengths = lines.take(line).map(_.length)
            val start = lengths.sum + lengths.length
            val end = start + lines(line).length
            s.setSpan(errorSpan, start, end,
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

          }
          case _ => {
            Log.wtf("Text parsing", e)
          }
        }
      }
    }

  }
  override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = {
    Log.d("Before", s.toString)
  }
  override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = {
    Log.d("on", s.toString)
    lazy val string: String = s.toString
    lazy val stringSeq: Seq[String] = string.split('\n').toSeq
    newLines = stringSeq
  }
}

class MapFragment(mindMap: MindMap) extends Fragment {

    val mindMapModel = new MindMapModel(mindMap)
    val androidTextWatcher: AndroidTextWatcher = new AndroidTextWatcher()
    androidTextWatcher.subscribe(mindMapModel)
    var mapTextFragment: MapTextFragment = new MapTextFragment(mindMap.getText(" "),
      androidTextWatcher)
    val mapDrawerFragment: MapDrawerFragment = new MapDrawerFragment(mindMap)
    mindMapModel.subscribe(mapDrawerFragment)

    override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
        savedInstanceState: Bundle): View = {
        // Defines the xml file for the fragment
        val result: View = inflater.inflate(R.layout.mapfragment, parent, false)
        val fragmentManager = getFragmentManager()
        fragmentManager.beginTransaction()
                       .replace(R.id.mapContent, mapTextFragment)
                       .replace(R.id.mapDrawer, mapDrawerFragment)
                       .commit()
        result
    }

    override def onViewCreated(view : View, savedInstanceState : Bundle) {
    }

    override def onActivityCreated(bundle: Bundle) {
      super.onActivityCreated(bundle)
    }

    def addListener(textWatcher: TextWatcher) =
        mapTextFragment.addListener(textWatcher)

}
