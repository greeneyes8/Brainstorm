package brainstorm.android
import android.widget.RelativeLayout 
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams
import android.view.LayoutInflater;

import android.view.View
import android.util.Log
import android.content.Context
import scala.math.{cos, sin, round}

class TreeRelativeLayout(context: Context, attr: AttributeSet) extends RelativeLayout(context, attr) {
  var rootView : View = _
  var rootMap : View = _
  var relativeLayout : RelativeLayout = _
  var idNodes : Array[Int] = Array()

  def computeX(position: (Float, Float)) = position._1 * java.lang.Math.cos(position._2)
  def computeY(position: (Float, Float)) = position._1 * java.lang.Math.sin(position._2)

  def findParentId(parent : View) = {
    var id = 0
    for (i <- 0 until idNodes.size) {
      if(parent.getId() == idNodes(i)) {
        id = i
      }         
    }
    id
  }

  def getParent(nodeView : Option[View]) = {
    nodeView match {
      case Some(vNode) => vNode.asInstanceOf[View]
      case None => new View(context)
      }
  }

  def polarView(view: View, position: (Float, Float)) = {
      var x : Int = round(computeX(position).toFloat)
      var y : Int = round(computeY(position).toFloat)
      Log.d("X and Y", "X: " ++ x.toString ++ " Y: " ++ y.toString)

      var params: RelativeLayout.LayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

      //setPadding(left, top, right, bottom)
      if(y > 0){
        params.addRule(RelativeLayout.ABOVE, R.id.rootNode)
        y = math.abs(y)
        if(x > 0) {
          params.addRule(RelativeLayout.RIGHT_OF, R.id.rootNode)
          params.setMargins(0, y, x, 0)
        } else {
          x = math.abs(x)
          params.addRule(RelativeLayout.LEFT_OF, R.id.rootNode)
          params.setMargins(x, y, 0, 0)
        }
      } else {
        params.addRule(RelativeLayout.BELOW, R.id.rootNode)
        y = math.abs(y)

        if(x > 0) {
          params.addRule(RelativeLayout.RIGHT_OF, R.id.rootNode)
          params.setMargins(0, 0, x, y)
        } else {
          x = math.abs(x)
          params.addRule(RelativeLayout.LEFT_OF, R.id.rootNode)
          params.setMargins(x, 0, 0, y)
        }
      }
      var nodeId = View.generateViewId()
      view.setId(nodeId)
      idNodes = idNodes :+ nodeId
      relativeLayout.addView(view, params)
  }

  def drawLine(viewPosition: (Float, Float), parentPosition: (Float, Float)) = {
    val rootV : View = relativeLayout.findViewById(R.id.rootNode).asInstanceOf[View]
    var coordsRoot : Array[Int] = Array()
    rootV.getLocationOnScreen(coordsRoot)

    var endX : Int = math.round(computeX(viewPosition).toFloat)
    var endY : Int = math.round(computeY(viewPosition).toFloat)


    var startX : Int = math.round(computeX(parentPosition).toFloat)
    var startY : Int = math.round(computeY(parentPosition).toFloat)
    val lineDrawer = new LineDrawer(context)
    lineDrawer.getCoords(coordsRoot(0)+startX, coordsRoot(1)+startY, coordsRoot(0)+endX, coordsRoot(1)+endY)
    relativeLayout.addView(lineDrawer)
  }

  def resetLayout() {
    idNodes = Array()
    relativeLayout.removeAllViews()
  }

  def paintViews(context: Context, views: Seq[(View, Option[View], (Float, Float))]) = {
    Log.d("Lambda", "views len: " ++ views.size.toString)
    rootView = LayoutInflater.from(context).inflate(R.layout.mapdrawer_fragment, this)
    relativeLayout = rootView.findViewById(R.id.rL).asInstanceOf[RelativeLayout]

      if(views.length == 0) {
        //get an empty layout
      } else {
        for(nodeView <- views) {
          nodeView._2 match {
            case Some(vNode) => {
              polarView(nodeView._1, nodeView._3)
              val parentId = findParentId(getParent(nodeView._2))

              drawLine(nodeView._3, views(parentId)._3)
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
