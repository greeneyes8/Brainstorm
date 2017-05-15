package brainstorm.android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import java.io.File

class MapActivity(val file:File) extends AppCompatActivity with TypedFindView {
  implicit val context = this

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.map)
    getFragmentManager().beginTransaction().replace(
      R.id.flContent, new MapTextFragment(file)).commit()
  }

  override def onPostCreate(savedInstanceState: Bundle) {
      super.onPostCreate(savedInstanceState)
  }
}
