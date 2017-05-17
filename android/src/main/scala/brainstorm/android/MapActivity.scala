package brainstorm.android

import scala.util.Try

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import android.content.Intent
import java.io.File
import java.net.URI

import brainstorm.core.Parser
import brainstorm.core.MindMap

class MapActivity extends DrawerLayoutActivity with TypedFindView {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    val tryFile: Try[File] = Try(getIntent.getExtras.get("file").asInstanceOf[URI]) 
      .flatMap(x => Try(new File(x)))
    val mindMap: MindMap = tryFile.flatMap(x => Try(Parser.parseFile(x.toURI)))
      .getOrElse(new MindMap("tmp"))
    setFragment(new MapTextFragment(mindMap.getText))
  }

  override def onPostCreate(savedInstanceState: Bundle) {
      super.onPostCreate(savedInstanceState)
  }
}
