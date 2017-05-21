package brainstorm.android

import scala.util.Try

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

import brainstorm.core.Parser
import brainstorm.core.MindMapModel
import brainstorm.core.MindMap

class AndroidMapModel(override val mindMap: MindMap) extends MindMapModel(mindMap) with TextWatcher {
  var startLine: Integer = _
  var oldLines: Seq[String] = _
  var newLines: Seq[String] = _

  override def afterTextChanged(s: Editable) = {
    textChange(startLine, oldLines, newLines)
  }
  override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = {
    lazy val string: String = s.toString
    startLine = string.take(start).count((x) => x == '\n')
    val endLine = string.take(start+count).count((x) => x == '\n')
    lazy val stringSeq: Seq[String] = string.split('\n').toSeq
    oldLines = stringSeq.slice(startLine, endLine+1)
  }
  override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = {
    lazy val string: String = s.toString
    startLine = string.take(start).count((x) => x == '\n')
    val endLine = string.take(start+count).count((x) => x == '\n')
    lazy val stringSeq: Seq[String] = string.split('\n').toSeq
    newLines = stringSeq.slice(startLine, endLine+1)
  }
}

class MapActivity extends DrawerLayoutActivity with TypedFindView {

  var mindMapModel: AndroidMapModel = _
  var mindMap : MindMap = _
  var fileOpt: Option[File] = None

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.map)
    afterOnCreate(savedInstanceState)

    val tryUri: Try[URI] = Try(getIntent.getExtras.get("file").asInstanceOf[URI]) 
    val tryFile: Try[File] = tryUri.flatMap(x => Try(new File(x)))
    fileOpt = tryFile.toOption
    val mindMap = tryFile.flatMap(x => Try(Parser.parseFile(x.toURI)))
      .getOrElse(new MindMap("tmp"))
      mindMapModel = new AndroidMapModel(mindMap)
      setFragment(new MapTextFragment(mindMap.getText))

    setFragment(new MapFragment(mindMap.getText))    
  }

  override def onPostCreate(savedInstanceState: Bundle) {
    super.onPostCreate(savedInstanceState)
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
        mindMapModel.mindMap.getText.foreach((x) => pw.println(x))
      }
      case None => {}
    }
    return super.onOptionsItemSelected(item)
  }
}
