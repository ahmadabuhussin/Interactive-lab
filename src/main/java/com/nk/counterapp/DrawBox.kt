package app.harshit.objectdetection

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.google.firebase.ml.vision.objects.FirebaseVisionObject

class DrawBox (context: Context, var visionObjects: List<FirebaseVisionObject>) : View(context) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val pen = Paint()
        for (item in visionObjects) {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = item.boundingBox
            canvas.drawRect(box, pen)
        }
    }
}