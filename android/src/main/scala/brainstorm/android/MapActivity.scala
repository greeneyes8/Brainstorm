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
import android.app.FragmentTransaction 
import android.widget.Toast
import android.util.Log
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.graphics.Color

import brainstorm.core.Parser
import brainstorm.core.MindMap


class MapActivity extends DrawerLayoutActivity with TypedFindView {

  var fileOpt: Option[File] = None
  var mapFragment: MapFragment = _
  //lazy val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

  override def onCreate(savedInstanceState: Bundle): Unit = {
    //PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    super.onCreate(savedInstanceState)
    //val prefText : String = sharedPreferences.getString("pref_TextColor", "no selection")
    //prefText match {
    //    case "BlackTextTheme" => super.setTheme(R.style.BlackTextTheme)
    //    case "WhiteTextTheme" => super.setTheme(R.style.WhiteTextTheme)
    //    case _ => super.setTheme(R.style.WhiteTextTheme)
    //}
    setContentView(R.layout.map)
    afterOnCreate(savedInstanceState)

    val tryUri: Try[URI] = Try(getIntent.getExtras.get("file").asInstanceOf[URI]) 

    val tryFile: Try[File] = tryUri.flatMap(x => Try(new File(x)))
    fileOpt = tryFile.toOption
    val mindMap = tryFile.flatMap(x => Try(Parser.parseFile(x.toURI)))
      .getOrElse(new MindMap("tmp"))
    mapFragment = new MapFragment(mindMap)

    //val prefMenu : String = sharedPreferences.getString("pref_MenuColor", "no selection")

    //val menuLayout = this.findViewById(R.id.navList)
    //menuLayout.setBackgroundColor(Color.parseColor(prefMenu))

    setFragment(mapFragment)
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
        mapFragment.mindMapModel.mindMap.getText(" ").foreach(pw.println)
        Toast.makeText(context, file.getName ++ " saved", 0).show
        pw.close()
      }
      case None => {
        // Ask for file name and other stuff
      }
    }
    return super.onOptionsItemSelected(item)
  }
}
