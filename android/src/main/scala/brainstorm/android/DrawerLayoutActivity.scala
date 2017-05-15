package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.TextView
import android.widget.ListView
import android.widget.Toast
import android.content.Intent
import TypedResource._

class DrawerLayoutActivity extends AppCompatActivity with TypedFindView {
    implicit val context = this
    var mDrawerToggle: ActionBarDrawerToggle = _
    var myArray : Array[String] = _
    var myListView : ListView = _ 
    var drawerLayout : DrawerLayout = _ 
    var mActivityTitle : String = _

    def setFragment(fragment: Fragment) {
        val fragmentManager = getFragmentManager()
        fragmentManager.beginTransaction()
                       .replace(R.id.flContent, fragment)
                       .commit()
    }

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
        myArray = Array(context.getResources().getString(R.string.action_AvailableMindMaps), 
                        context.getResources().getString(R.string.action_AddMapinRealTime), 
                        context.getResources().getString(R.string.action_GeneralSettings))
        val adapter = new ArrayAdapter[String](context, android.R.layout.simple_list_item_1, myArray)
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

    //position starts from 1 and myArray form 0 
    def getFragmentClass(position: Int) =  position-1 match {
        case 0 => {
            setTitle(R.string.action_AvailableMindMaps)
            val fragmentManager = getFragmentManager()
            fragmentManager.beginTransaction()
                           .replace(R.id.flContent, new MainFragment())
                           .commit()
        }
        case 2 => {
            setTitle(R.string.action_GeneralSettings)
            val fragmentManager = getFragmentManager()
            fragmentManager.beginTransaction()
                           .replace(R.id.flContent, new SettingsFragment())
                           .commit()
        }
        case 1 => {
            setTitle(R.string.action_AddMapinRealTime)
            val intent : Intent = new Intent(this, classOf[MapActivity])
            startActivity(intent)
        }
        case _ => {
            setTitle(position.toString())
            val fragmentManager = getFragmentManager()
            fragmentManager.beginTransaction()
                           .replace(R.id.flContent, new SettingsFragment())
                           .commit()
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
            setTitle(R.string.action_settings)
            mDrawerToggle.syncState()
            val fragmentManager = getFragmentManager()
                fragmentManager.beginTransaction()
                               .replace(R.id.flContent, new SettingsFragment())
                               .commit()
            return true
        } else if (id == R.id.action_about) {
            setTitle(R.string.action_about)
            mDrawerToggle.syncState()
            val fragmentManager = getFragmentManager()
                fragmentManager.beginTransaction()
                               .replace(R.id.flContent, new AuthorsFragment())
                               .commit()
            return true
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
