package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.content.res.Configuration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.graphics.Color 
import android.widget.Toast
import TypedResource._

class MainActivity extends DrawerLayoutActivity with TypedFindView {
    lazy val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    override def onCreate(savedInstanceState: Bundle): Unit = {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        super.onCreate(savedInstanceState)
        //val prefText : String = sharedPreferences.getString("pref_TextColor", "no selection")
        
        //prefText match {
       //     case "BlackTextTheme" => super.setTheme(R.style.BlackTextTheme)
       //     case "WhiteTextTheme" => super.setTheme(R.style.WhiteTextTheme)
       //     case _ => super.setTheme(R.style.WhiteTextTheme)
      //  }
        
        setContentView(R.layout.main)
        afterOnCreate(savedInstanceState)

     //   val prefMenu : String = sharedPreferences.getString("pref_MenuColor", "#FFFFFF")

        val menuLayout = this.findViewById(R.id.navList)
       // menuLayout.setBackgroundColor(Color.parseColor(prefMenu))

        setFragment(new MainFragment())
    }

}
