package brainstorm.android

import scala.util.Try

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import android.content.Intent
import java.io.File
import java.net.URI
import android.view.Menu
import android.view.MenuItem
import android.text.TextWatcher
import android.text.Editable

import brainstorm.core.Parser
import brainstorm.core.MindMapModel
import brainstorm.core.MindMap

class AndroidMapModel(override val mindMap: MindMap) extends MindMapModel(mindMap) with TextWatcher {
  override def afterTextChanged(s: Editable) = {}
  override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = {}
  override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = {}
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
    setFragment(new MapTextFragment(mindMap.getText))
  }

  override def onPostCreate(savedInstanceState: Bundle) {
      super.onPostCreate(savedInstanceState)
  }

  override def onCreateOptionsMenu(menu : Menu) : Boolean = {
        //Adds items to the ActionBar
        getMenuInflater().inflate(R.menu.menu_map, menu)
        return true
    }

}
