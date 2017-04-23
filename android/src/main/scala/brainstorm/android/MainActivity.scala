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

class MainActivity extends AppCompatActivity with TypedFindView{

    implicit val context = this
    var mDrawerToogle: ActionBarDrawerToggle = _

    override def onCreate(savedInstanceState: Bundle): Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        //val vh: TypedViewHolder.main = TypedViewHolder.setContentView(this, TR.layout.main)
        //vh.text.setText(s"Hello world, from ${TR.string.app_name.value}")
        //findView(TR.text).setText("Hello again, world!")
        
        val myList = Array("Add Map From File", "Add Map in RealTime", "General Settings")
        val myListView = findView(TR.navList)
        val drawerLayout = findView(TR.drawer_layout)

        val adapter = new ArrayAdapter[String](context, android.R.layout.simple_list_item_1, myList)
        myListView.setAdapter(adapter)

        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        getSupportActionBar().setHomeButtonEnabled(true)

        val inflater : LayoutInflater = getLayoutInflater();
        val mTop : ViewGroup = inflater.inflate(R.layout.header_listview_menu, myListView, false).asInstanceOf[ViewGroup];
        myListView.addHeaderView(mTop, null, false);

        val fragmentManager = getFragmentManager()
                    fragmentManager.beginTransaction()
                                .replace(R.id.flContent, new MainFragment())
                                .commit()

        myListView.setOnItemClickListener(new OnItemClickListener() {
            override def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
                Toast.makeText(
                getApplicationContext(),
                "Time for an upgrade!",
                Toast.LENGTH_SHORT
                ).show()

                drawerLayout.closeDrawer(myListView);

                myListView.setItemChecked(position, true)

                var fragment = getFragmentClass(myList, position)

                val fragmentManager = getFragmentManager()
                    fragmentManager.beginTransaction()
                                .replace(R.id.flContent, fragment.newInstance())
                                .commit()

                //setTitle(myList(position));
                
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

    def getFragmentClass(myList : Array[_], position: Int) =  myList(position) match {
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
}
