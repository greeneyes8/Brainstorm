package brainstorm.android

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import TypedResource._

class AuthorsActivity extends AppCompatActivity with TypedFindView {
    var buttonBack : Button = _
    var textViewAuthors : TextView = _

    override def onCreate(savedInstanceState: Bundle): Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        buttonBack = findView(TR.buttonBack)
        textViewAuthors = findView(TR.textViewAuthors)

        buttonBack.setOnClickListener(new View.OnClickListener() {

            override def onClick(view : View) {
                //val intent : Intent = new Intent(this, MainActivity.class)
                //startActivity(intent)
                //textViewAuthors.text = "Elo"
            }
        });

    }


}