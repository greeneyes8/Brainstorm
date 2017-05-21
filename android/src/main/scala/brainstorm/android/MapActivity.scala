package brainstorm.android

import scala.util.Try

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import android.content.Intent
import java.io.File
import java.net.URI

import brainstorm.core.Parser
import brainstorm.core.MindMapModel
import brainstorm.core.MindMap

class AndroidMapModel(val mindMap: MindMap) extends MindMapModel(mindMap) with TextWatcher {
  override def afterTextChanged(s: Editable) = {}
  override def beforeTextChanged(s: CharSequence, start: Integer, count: Integer, after: Integer) = {}
  override def onTextChanged(s: CharSequence, start: Integer, before: Integer, count: Integer) = {}
}

class MapActivity extends DrawerLayoutActivity with TypedFindView {

  var mindMapModel: AndroidMapModel = _

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    val tryFile: Try[File] = Try(getIntent.getExtras.get("file").asInstanceOf[URI]) 
      .flatMap(x => Try(new File(x)))
    val mindMap = tryFile.flatMap(x => Try(Parser.parseFile(x.toURI)))
      .getOrElse(new MindMap("tmp"))
    mindMapModel = new AndroidMapModel(mindMap)
    setFragment(new MapTextFragment(mindMapModel))
  }

  override def onPostCreate(savedInstanceState: Bundle) {
      super.onPostCreate(savedInstanceState)
  }
}
