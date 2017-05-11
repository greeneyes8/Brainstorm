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
import TypedResource._

class MainActivity extends AppCompatActivity with TypedFindView {
    // allows accessing `.value` on TR.resource.constants
    implicit val context = this
    var mDrawerToogle: ActionBarDrawerToggle = _
    var myArray : Array[String] = Array("Add Map From File", "Add Map in RealTime", "General Settings")
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
        mDrawerToogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            override def onDrawerOpened(drawerView : View) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            override def onDrawerClosed(view : View) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getTitle().toString());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToogle.setDrawerIndicatorEnabled(true)
        drawerLayout.setDrawerListener(mDrawerToogle)
    }

    private def addDrawerItems() {
        val adapter = new ArrayAdapter[String](context, android.R.layout.simple_list_item_1, myArray)
        myListView.setAdapter(adapter)

        myListView.setOnItemClickListener(new OnItemClickListener() {
            override def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
                Toast.makeText(
                getApplicationContext(),
                "Time for an upgrade!",
                Toast.LENGTH_SHORT
                ).show()

                drawerLayout.closeDrawer(myListView);

                myListView.setItemChecked(position, true)

                var fragment = getFragmentClass(myArray, position)

                val fragmentManager = getFragmentManager()
                    fragmentManager.beginTransaction()
                                .replace(R.id.flContent, fragment.newInstance())
                                .commit()

                //setTitle(myArray(position));
                
                //val fragment = new SettingsFragment();
                //val args = new Bundle();
                //args.putInt(SettingsFragment.ARG_PLANET_NUMBER, position);
                //fragment.setArguments(args);

                // Insert the fragment by replacing any existing fragment
                //val fragmentManager = getFragmentManager();
               // fragmentManager.beginTransaction()
                //                .replace(R.id.mainfragment, fragment.asInstanceOf[Fragment])
                //                .commit();


            }
        })
    }

    def getFragmentClass(myArray : Array[_], position: Int) =  myArray(position) match {
        case "General Settings" => classOf[SettingsFragment]
        case "Add Map From File" => classOf[MainFragment]
        case _ => classOf[MainFragment]
    }

    override def onPostCreate(savedInstanceState: Bundle) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToogle.syncState()
    }

    override def onConfigurationChanged(newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToogle.onConfigurationChanged(newConfig)
    }

     override def onCreateOptionsMenu(menu : Menu) : Boolean = {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override def onOptionsItemSelected(item : MenuItem) : Boolean = {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id : Int = item.getItemId()

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        // Activate the navigation drawer toggle
        if (mDrawerToogle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
