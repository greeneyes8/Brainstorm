package brainstorm.android

import android.app.Fragment
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import TypedResource._

class AuthorsFragment extends Fragment {

    override def onCreateView(inflater: LayoutInflater, parent: ViewGroup,
      savedInstanceState: Bundle): View = {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.authors_fragment, parent, false)
    }

    override def onViewCreated(view : View, savedInstanceState : Bundle) {

        //val buttonBack : Button = getActivity.findViewById(R.id.buttonBack).asInstanceOf[Button]
        //val textViewAuthors : TextView = getActivity.findViewById(R.id.textViewAuthors).asInstanceOf[TextView]

        //buttonBack.setOnClickListener(new View.OnClickListener() {

            //override def onClick(view : View) {
                //val intent : Intent = new Intent(this, MainActivity.class)
                //startActivity(intent)
                //textViewAuthors.setText("Elo")
           // }
       // });

    }


}