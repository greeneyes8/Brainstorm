package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.content.res.Configuration
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.preference.PreferenceManager 
import android.content.SharedPreferences 
import android.content.pm.ApplicationInfo 
import TypedResource._

class MainActivity extends DrawerLayoutActivity with TypedFindView {

    def getApplicationName(context: Context): String = {
      val applicationInfo = context.getApplicationInfo();
      val stringId = applicationInfo.labelRes;
      if (stringId == 0) {
        applicationInfo.nonLocalizedLabel.toString()
      } else {
        context.getString(stringId);
      }
    }

    override def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.main)
        afterOnCreate(savedInstanceState)
        setTitle(getApplicationName(this))

        val menuLayout = this.findViewById(R.id.navList)

        setFragment(new MainFragment())
    }

}
