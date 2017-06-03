package brainstorm.android
import android.view.View
import android.graphics.Canvas 
import android.graphics.Paint 
import android.graphics.Color 
import android.content.Context

class LineDrawer(context: Context) extends View(context){
    var startX : Float = _
    var startY : Float = _
    var endX : Float = _
    var endY : Float = _

    def getCoords(X1 : Float, Y1: Float, X2: Float, Y2: Float){
        startX = X1
        startY = Y1
        endX = X2
        endY = Y2
    }

    override def onDraw(canvas : Canvas) {
        super.onDraw(canvas)
        val paint : Paint = new Paint()
        paint.setColor(Color.BLACK)
        paint.setStrokeWidth(10)

        canvas.drawLine(startX, startY, endX, endY, paint)
    }

}