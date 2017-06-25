package brainstorm.android

import scala.util.Try
import scala.util.Success
import scala.util.Failure

import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.net.URI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.app.FragmentTransaction 
import android.widget.Toast
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.util.Log

import brainstorm.core.Parser
import brainstorm.core.MindMap


class MapActivity extends DrawerLayoutActivity with TypedFindView with
  TextDialogListener {
  var fileOpt: Option[File] = None
  var mapFragment: MapFragment = _

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)


    val tryUri: Try[URI] = Try(getIntent.getExtras.get("file").asInstanceOf[URI]) 
    val tryFile: Try[File] = tryUri.flatMap(x => Try(new File(x)))
    fileOpt = tryFile.toOption
    val mindMapOpt = fileOpt.map(x => Parser.parseFile(x.toURI))

    mindMapOpt match {
      case Some(mindMap) => setTitle(mindMap.name)
      case None => setTitle(R.string.rtmap_t)
    }

    val mindMap = mindMapOpt.getOrElse(new MindMap("tmp"))
    mapFragment = new MapFragment(mindMap)

    setFragment(mapFragment)
  }

  override def onCreateOptionsMenu(menu : Menu) : Boolean = {
    val prefStyle : String = mySharedPreferences.getString("pref_Style", "no selection")

    prefStyle match {
      case "BlackStyle" => getMenuInflater().inflate(R.menu.menu_map, menu)
      case "WhiteStyle" => getMenuInflater().inflate(R.menu.menu_map_black, menu)
      case i => Log.d("onCreateOptionsMenu", "incorrect" ++ i.toString)
    }
    true
  }

  def saveFile(file: File) {
    val pw = new PrintWriter(file)
    mapFragment.mindMapModel.mindMap.getText(" ").foreach(pw.println)
    Toast.makeText(context, file.getName ++ " saved", 0).show
    pw.close
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    fileOpt match {
      case Some(file) => saveFile(file)
      case None => {
        val dialog = new TextDialog(MapActivity.this, R.string.rtsaveTitle, R.string.mmName)
        dialog.show(getFragmentManager(), "missiles")
      }
    }
    super.onOptionsItemSelected(item)
  }

  override def onPositive(name: String) {
    val mapsRootFile: File = new File(getFilesDir, "maps/")
    val file = new File(mapsRootFile, name)
    fileOpt = Some(file)
    saveFile(file)
    mapFragment.mindMapModel.mindMap.name = name
    setTitle(name)
  }
}
