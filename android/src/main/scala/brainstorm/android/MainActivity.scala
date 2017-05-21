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

    override def onPrepareOptionsMenu(menu : Menu) : Boolean = {
        menu.findItem(R.id.menu_item_add).setVisible(false)
        menu.findItem(R.id.action_about).setVisible(true)
        return super.onPrepareOptionsMenu(menu)
    }

}
