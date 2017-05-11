package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity
import android.graphics.drawable.Animatable
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
import android.widget.TextView
import android.widget.ListView
import android.widget.Toast
import android.content.Intent
import TypedResource._

class MainActivity extends AppCompatActivity with TypedFindView {
    implicit val context = this
    var mDrawerToggle: ActionBarDrawerToggle = _
    var myArray : Array[String] = Array("List of available maps", "Add Map From File", "Add Map in RealTime", "General Settings")
    var myListView : ListView = _ 
    var drawerLayout : DrawerLayout = _ 
    var mActivityTitle : String = _

    override def onCreate(savedInstanceState: Bundle): Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        
        myListView = findView(TR.navList)
        drawerLayout = findView(TR.drawer_layout)
        mActivityTitle = getTitle.toString()

        addDrawerItems()
        setupDrawer()

        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        getSupportActionBar().setHomeButtonEnabled(true)

        val inflater : LayoutInflater = getLayoutInflater();
        val mTop : ViewGroup = inflater.inflate(R.layout.header_listview_menu, myListView, false).asInstanceOf[ViewGroup];
        myListView.addHeaderView(mTop, null, false);

        val fragmentManager = getFragmentManager()
                    fragmentManager.beginTransaction()
                                .replace(R.id.flContent, new MainFragment())
                                .commit()
        
    }

    private def setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            override def onDrawerOpened(drawerView : View) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            override def onDrawerClosed(view : View) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getTitle().toString());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true)
        drawerLayout.setDrawerListener(mDrawerToggle)
    }

    private def addDrawerItems() {
        val adapter = new ArrayAdapter[String](context, android.R.layout.simple_list_item_1, myArray)
        myListView.setAdapter(adapter)
        myListView.setItemChecked(1, true)

        myListView.setOnItemClickListener(new OnItemClickListener() {
            override def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
                drawerLayout.closeDrawer(myListView);

                myListView.setItemChecked(position, true)

                var fragment = getFragmentClass(position)

                val fragmentManager = getFragmentManager()
                    fragmentManager.beginTransaction()
                                .replace(R.id.flContent, fragment.newInstance())
                                .commit()
            }
        })
    }

    //position starts from 1 and myArray form 0 
    def getFragmentClass(position: Int) =  myArray(position-1) match {
        case "List of available maps" => classOf[MainFragment]
        case "General Settings" => classOf[SettingsFragment]
        case "Add Map From File" => classOf[MainFragment]
        case _ => {
            setTitle(position.toString())
            classOf[SettingsFragment]
            }
    }

    override def onPostCreate(savedInstanceState: Bundle) {
        super.onPostCreate(savedInstanceState)
        //Sync the Toggle and the title faster after the change
        mDrawerToggle.syncState()
    }

    override def onConfigurationChanged(newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

     override def onCreateOptionsMenu(menu : Menu) : Boolean = {
        //Adds items to the ActionBar
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override def onOptionsItemSelected(item : MenuItem) : Boolean = {
        val id : Int = item.getItemId()

        if (id == R.id.action_settings) {
            setTitle("Settings")
            return true
        } else if (id == R.id.action_authors) {
            setTitle("Authors")
            val intent : Intent = new Intent(context, classOf[AuthorsActivity])
            startActivity(intent)
            return true
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
