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
    //var mDrawerToogle

    override def onCreate(savedInstanceState: Bundle): Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        // type ascription is required due to SCL-10491
        //val vh: TypedViewHolder.main = TypedViewHolder.setContentView(this, TR.layout.main)
        //vh.text.setText(s"Hello world, from ${TR.string.app_name.value}")
        //findView(TR.text).setText("Hello again, world!")
        

        val myList = Array("Paris", "New York", "Tokyo", "Berlin", "Copenhagen")
        val myListView = findView(TR.navList)
        val drawerLayout = findView(TR.drawer_layout)

        val adapter = new ArrayAdapter[String](context, android.R.layout.simple_list_item_1, myList)
        myListView.setAdapter(adapter)

        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        getSupportActionBar().setHomeButtonEnabled(true)

        myListView.setOnItemClickListener(new OnItemClickListener() {
            override def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
                Toast.makeText(
                getApplicationContext(),
                "Time for an upgrade!",
                Toast.LENGTH_SHORT
                ).show()

                myListView.setItemChecked(position, true)
                if (position == 0){
                    val fragment = new SettingsFragment()
                    val fragmentManager = getFragmentManager()
                    fragmentManager.beginTransaction()
                                .replace(R.id.flContent, fragment)
                                .commit()
                } else {
                  setTitle(myList(position));
                }

                //setTitle(myList(position));
                //drawerLayout.closeDrawer(myListView);
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
        
        val mDrawerToogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

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

        //vh.image.getDrawable match {
        //  case a: Animatable => a.start()
        //  case _ => // not animatable
        //}

    }

    //override def onPostCreate(savedInstanceState: Bundle) {
    //    super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
     //   mDrawerToogle.syncState()
    //}

    //override def onConfigurationChanged(newConfig : Configuration) {
   //     super.onConfigurationChanged(newConfig)
   //     mDrawerToogle.onConfigurationChanged(newConfig)
   // }

     override def onCreateOptionsMenu(menu : Menu) : Boolean = {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }
}