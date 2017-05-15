package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.content.res.Configuration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import TypedResource._

class MainActivity extends DrawerLayoutActivity with TypedFindView {

    override def onCreate(savedInstanceState: Bundle): Unit = {
        super.onCreate(savedInstanceState)

        setFragment(new MainFragment())
        
    }

    override def onPostCreate(savedInstanceState: Bundle) {
        super.onPostCreate(savedInstanceState)
    }

    override def onConfigurationChanged(newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
    }

     override def onCreateOptionsMenu(menu : Menu) : Boolean = {
        //Adds items to the ActionBar
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }
}
