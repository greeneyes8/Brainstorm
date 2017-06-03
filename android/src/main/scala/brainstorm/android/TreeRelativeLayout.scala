package brainstorm.android
import android.widget.RelativeLayout 
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams
import android.view.LayoutInflater;

import android.view.View
import android.content.Context
import scala.math

class TreeRelativeLayout(context: Context) extends RelativeLayout(context) {
  var rootView : View = _
  var rootMap : View = _

  def computeX(position: (Float, Float)) = position._1 * math.cos(position._2)
  def computeY(position: (Float, Float)) = position._1 * math.sin(position._2)

  def this(context: Context, views: Seq[(View, Option[View], (Float, Float))]) = {
      this(context)
      paintViews(context, views)
  }

  def polarView(view: View, position: (Float, Float)) = {
      var x : Int = math.round(computeX(position).toFloat)
      var y : Int = math.round(computeY(position).toFloat)

      var params: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

      //setPadding(left, top, right, bottom)
      if(y > 0){
        params.addRule(RelativeLayout.ABOVE, R.id.rootNode)
        y = math.abs(y)
        if(x > 0) {
          view.setPadding(0, y, x, 0)
        } else {
          x = math.abs(x)
          view.setPadding(x, y, 0, 0)
        }
      } else {
        params.addRule(RelativeLayout.BELOW, R.id.rootNode)
        y = math.abs(y)

        if(x > 0) {
          view.setPadding(0, 0, x, y)
        } else {
          x = math.abs(x)
          view.setPadding(x, 0, 0, y)
        }
      }
  }

  def drawLine(view: View, parentView: View) = {

  }

  def paintViews(context: Context, views: Seq[(View, Option[View], (Float, Float))]) = {
    rootView = LayoutInflater.from(context).inflate(R.layout.mapdrawer_fragment, this)
    var relativeLayout = rootView.findViewById(R.id.rL).asInstanceOf[RelativeLayout]

      if(views.length == 0) {
        //get an empty layout
      } else {
        for(nodeView <- views) {
          nodeView._2 match {
            case Some(vNode) => {
              polarView(nodeView._1, nodeView._3)

            }
            case None => {
              nodeView._1.setId(R.id.rootNode)
              var params: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
              params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
              relativeLayout.addView(nodeView._1, params)
            }
          }
        }
      }
  }
}
