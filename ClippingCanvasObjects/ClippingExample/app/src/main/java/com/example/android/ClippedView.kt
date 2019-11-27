package com.example.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


//DONE: Step 1.1 At the same level as MainActivity.kt,
// create a new Kotlin file and class for a custom view called ClippedView that extends View.
// The @JvmOverloads annotation instructs the Kotlin compiler to generate overloads
// for this function that substitute default parameter values.
class ClippedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    // DONE: Step 3.0 define a variable paint of a Paint.
//      a. Enable anti-aliasing,
//      b. and use the stroke width and
//      c. text size defined in the dimensions.
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = resources.getDimension(R.dimen.strokeWidth)
        textSize = resources.getDimension(R.dimen.textSize)
    }
    // DONE: Step 3.1 create and initialize a path variable of a Path
    //  to store locally the path of what has been drawn. Import android.graphics.Path.
    private val path = Path()

    // DONE: Step 4.0 add variables for dimensions for a clipping rectangle
    //  around the whole set of shapes
    private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
    private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
    private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
    private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)

    // DONE: Step 4.1 Add variables for the inset of a rectangle and the offset of a small rectangle.
    private val rectInset = resources.getDimension(R.dimen.rectInset)
    private val smallRectOffset = resources.getDimension(R.dimen.smallRectOffset)
    // DONE: Step 4.2 Add a variable for the radius of a circle.
    //  This is the circle that is drawn inside the rectangle.
    private val circleRadius = resources.getDimension(R.dimen.circleRadius)
    // DONE: Step 4.3 Add an offset and a text size for text that is drawn inside the rectangle
    private val textOffset = resources.getDimension(R.dimen.textOffset)
    private val textSize = resources.getDimension(R.dimen.textSize)
    // DONE: Step 5.0 Set up the coordinates for two columns
    private val columnOne = rectInset
    private val columnTwo = columnOne + rectInset + clipRectRight
    // DONE: Step 5.1 Add the coordinates for each row,
    //  including the final row for the transformed text
    private val rowOne = rectInset
    private val rowTwo = rowOne + rectInset + clipRectBottom
    private val rowThree = rowTwo + rectInset + clipRectBottom
    private val rowFour = rowThree + rectInset + clipRectBottom
    private val textRow = rowFour + (1.5f * clipRectBottom)

    // DONE: Step 6.0 Override onDraw() and call a function for each shape you are drawing.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawClippedRectangle(canvas)
        drawBackAndUnclippedRectangle(canvas)
        drawDifferenceClippingExample(canvas)
        drawCircularClippingExample(canvas)
        drawIntersectionClippingExample(canvas)
        drawCombinedClippingExample(canvas)
        drawRoundedRectangleClippingExample(canvas)
        drawOutsideClippingExample(canvas)
        drawSkewedTextExample(canvas)
        drawTranslatedTextExample(canvas)
        // drawQuickRejectExamples(canvas)
    }

    // DONE: Step 6.1 Create stubs for each of the drawing functions
    //  so that the code will continue to compile.
    private fun drawTranslatedTextExample(canvas: Canvas?) {
    }

    private fun drawSkewedTextExample(canvas: Canvas?) {
    }

    private fun drawOutsideClippingExample(canvas: Canvas?) {
    }

    private fun drawRoundedRectangleClippingExample(canvas: Canvas?) {
    }

    private fun drawCombinedClippingExample(canvas: Canvas?) {
    }

    private fun drawIntersectionClippingExample(canvas: Canvas?) {
    }

    private fun drawCircularClippingExample(canvas: Canvas?) {
    }

    private fun drawDifferenceClippingExample(canvas: Canvas?) {
    }

    private fun drawBackAndUnclippedRectangle(canvas: Canvas?) {

    }

    // DONE: Step 7.0 Create a drawClippedRectangle() method that takes an argument canvas of type Canvas
    private fun drawClippedRectangle(canvas: Canvas?) {

        canvas?.apply {
            // NOTE STEP 7.* Try to comment the clipRect method and/or change the value of draw's
            // methods to notice how clipping constraint the boundary draw of whole canvas

            // DONE: Step 7.1 set the boundaries of the clipping rectangle for the whole shape.
            //  Apply a clipping rectangle that constrains to drawing only the square
            clipRect(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom)
            // DONE: Step 7.2 Add code to fill the canvas with white color.
            //  Only the region inside the clipping rectangle will be filled!
            drawColor(Color.WHITE)
            // DONE: Step 7.2 Change the color to red and draw a diagonal line inside the clipping rectangle.
            paint.color = Color.RED
            drawLine(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom, paint)
            // DONE: Step 7.3 Set the color to green and draw a circle inside the clipping rectangle.
            paint.color = Color.GREEN
            drawCircle(circleRadius, clipRectBottom - circleRadius, circleRadius, paint)
            // DONE: Step 7.4 Set the color to blue and using Canvas.drawText()
            //  draw the text aligned with the right edge of the clipping rectangle
            paint.color = Color.BLUE
            paint.textSize = textSize
            // Note: The Paint.Align property specifies which side of the text to align to the origin
            // (not which side of the origin the text goes, or where in the region it is aligned!)
            // . Aligning the right side of the text to the origin places it on the left of the origin.
            paint.textAlign = Paint.Align.RIGHT
            drawText(context.getString(R.string.clipping), clipRectRight, textOffset, paint)
        }
    }


}
