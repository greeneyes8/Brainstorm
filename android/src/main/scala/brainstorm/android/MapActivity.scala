package brainstorm.android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import java.io.File

class MapActivity(val file:File) extends DrawerLayoutActivity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setFragment(new MapTextFragment(file))
  }

  override def onPostCreate(savedInstanceState: Bundle) {
      super.onPostCreate(savedInstanceState)
  }
}
