package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.content.res.Configuration
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarActivity
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater 
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.content.Intent
import android.content.Context 
import TypedResource._
import android.util.Log 

import brainstorm.core.MindMap

class DrawerLayoutActivity extends AppCompatActivity with TypedFindView {
  implicit val context = this

  lazy val mySharedPreferences: SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(this)

  lazy val mDrawerToggle: ActionBarDrawerToggle = 
    new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open,
      R.string.drawer_close) {

      override def onDrawerOpened(drawerView : View) {
        super.onDrawerOpened(drawerView);
        getSupportActionBar().setTitle("Menu");
        invalidateOptionsMenu();
      }

      override def onDrawerClosed(view : View) {
        super.onDrawerClosed(view);
        getSupportActionBar().setTitle(getTitle().toString());
        invalidateOptionsMenu();
      }

    }

  lazy val myListView : ListView = findView(TR.navList) 

  lazy val drawerLayout : DrawerLayout = findView(TR.drawer_layout)

  lazy val myArray : Array[String] = 
    Array(context.getResources().getString(R.string.action_AvailableMindMaps), 
      context.getResources().getString(R.string.action_AddMapinRealTime), 
      context.getResources().getString(R.string.action_GeneralSettings))

  def setFragment(fragment: Fragment) {
      val fragmentManager = getFragmentManager()
      fragmentManager.beginTransaction()
                     .replace(R.id.flContent, fragment)
                     .commit()
  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false)


    val prefStyle : String = mySharedPreferences.getString("pref_Style", "no selection")

    prefStyle match {
      case "BlackStyle" => super.setTheme(R.style.DarkStyle)
      case "WhiteStyle" => super.setTheme(R.style.LightStyle)
      case _ => super.setTheme(R.style.DarkStyle)
    }
    setContentView(R.layout.main)
  }

  override def onRestart() {
    super.onRestart()
    recreate()
  }

  override def onPostCreate(savedInstanceState: Bundle) {
    super.onPostCreate(savedInstanceState)
    addDrawerItems()
    setupDrawer()

    getSupportActionBar().setDisplayHomeAsUpEnabled(true)
    getSupportActionBar().setHomeButtonEnabled(true)
    mDrawerToggle.syncState()
  }

  private def setupDrawer() {
    mDrawerToggle.setDrawerIndicatorEnabled(true)
    drawerLayout.setDrawerListener(mDrawerToggle)
  }

  private def addDrawerItems() {
    val adapter = new ArrayAdapter[String](
      context, android.R.layout.simple_list_item_1, myArray)
    myListView.setAdapter(adapter)
    myListView.setItemChecked(1, true)
    myListView.setOnItemClickListener(new OnItemClickListener() {
      override def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        drawerLayout.closeDrawer(myListView);
        myListView.setItemChecked(position, true)
        getFragmentClass(position)
      }
    })
  }

  def getFragmentClass(position: Int) =  position match {
    case 0 => {
      val intent : Intent = new Intent(this, classOf[MainActivity])
      startActivity(intent)
    }
    case 1 => {
      val intent : Intent = new Intent(this, classOf[MapActivity])
      startActivity(intent)
    }
    case 2 => {
      val intent : Intent = new Intent(this, classOf[SettingsActivity])
      startActivity(intent)
    }
    case i => {
      Log.e("Drawer menu", "Position " ++ i.toString ++ " clicked")
    }
  }

  override def onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    mDrawerToggle.onConfigurationChanged(newConfig)
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater().inflate(R.menu.menu_main, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    val id: Int = item.getItemId()

    id match {
      case R.id.action_about => {
        val fragmentManager = getFragmentManager()
        fragmentManager.beginTransaction()
                       .replace(R.id.flContent, new AuthorsFragment())
                       .addToBackStack(null)
                       .commit()
          true
      }
      case _ => mDrawerToggle.onOptionsItemSelected(item)
    }
  }
}
