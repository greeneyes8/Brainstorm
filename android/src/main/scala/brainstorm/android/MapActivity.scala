package brainstorm.android

import scala.util.Try
import scala.util.Success
import scala.util.Failure

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import android.content.Intent
import java.io.File
import java.io.PrintWriter
import java.net.URI
import android.view.Menu
import android.view.MenuItem
import android.text.TextWatcher
import android.text.Editable
import android.app.FragmentTransaction 
import android.widget.Toast
import android.util.Log

import brainstorm.core.Parser
import brainstorm.core.MindMapModel
import brainstorm.core.MindMap

class AndroidMapModel(override val mindMap: MindMap) extends MindMapModel(mindMap) with TextWatcher {
  var startLine: Integer = _
  var oldLines: Seq[String] = _
  var newLines: Seq[String] = _
  var processed: Boolean = true

  override def afterTextChanged(s: Editable) = {
    Log.d("Args", startLine.toString ++ " " ++ oldLines.zipWithIndex.toString ++ " " ++ newLines.zipWithIndex.toString)
    val parseTry = Try(textChange(startLine, oldLines, newLines))
    parseTry match {
      case Success(_) => {}
      case Failure(e) => { Log.wtf("Problem", e)
      }
    }

  }
  override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = {
    Log.d("Before", s.toString)
    lazy val string: String = s.toString
    val startLine2 = string.take(start).count((x) => x == '\n')
    val endLine = string.take(start+count).count((x) => x == '\n')
    lazy val stringSeq: Seq[String] = string.split('\n').toSeq
    val oldLines2 = stringSeq.slice(startLine2, endLine+1)
    startLine = startLine2
    oldLines = oldLines2

  }
  override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = {
    Log.d("on", s.toString)
    lazy val string: String = s.toString
    val endLine = string.take(start+count).count((x) => x == '\n')
    lazy val stringSeq: Seq[String] = string.split('\n').toSeq
    val newLines2 = stringSeq.slice(startLine, endLine+1)
    newLines = newLines2
  }
}

class MapActivity extends DrawerLayoutActivity with TypedFindView {

  var mindMapModel: AndroidMapModel = _
  var mindMap : MindMap = _
  var fileOpt: Option[File] = None
  var mapTextFragment: MapFragment = _

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.map)
    afterOnCreate(savedInstanceState)

    val tryUri: Try[URI] = Try(getIntent.getExtras.get("file").asInstanceOf[URI]) 
    Log.d("File", tryUri.get.toString)
    val tryFile: Try[File] = tryUri.flatMap(x => Try(new File(x)))
    fileOpt = tryFile.toOption
    val mindMap = tryFile.flatMap(x => Try(Parser.parseFile(x.toURI)))
      .getOrElse(new MindMap("tmp"))
    mindMapModel = new AndroidMapModel(mindMap)
    mapTextFragment = new MapFragment(mindMap.getText)
    setFragment(mapTextFragment)
  }

  override def onPostCreate(savedInstanceState: Bundle) {
    super.onPostCreate(savedInstanceState)
    mapTextFragment.addListener(mindMapModel)
  }

  override def onCreateOptionsMenu(menu : Menu) : Boolean = {
    //Adds items to the ActionBar
    getMenuInflater().inflate(R.menu.menu_map, menu)
    return true
  }
  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    fileOpt match {
      case Some(file) => {
        val pw = new PrintWriter(file)
        mindMapModel.mindMap.getText.foreach(pw.println)
        Toast.makeText(context, file.getName ++ " saved", 0).show
        pw.close()
      }
      case None => {}
    }
    return super.onOptionsItemSelected(item)
  }
}
