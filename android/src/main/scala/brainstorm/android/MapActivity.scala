package brainstorm.android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import java.io.File

class MapActivity extends DrawerLayoutActivity with TypedFindView {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setFragment(new MapTextFragment)
  }

  override def onPostCreate(savedInstanceState: Bundle) {
      super.onPostCreate(savedInstanceState)
  }
}
