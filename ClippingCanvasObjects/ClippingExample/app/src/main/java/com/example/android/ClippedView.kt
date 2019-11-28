package com.example.android

import android.content.Context
import android.graphics.*
import android.os.Build
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

    // DONE: Step 13.0 create and initialize a rectangle variable.
    //  RectF is a class that holds rectangle coordinates in floating point
    private var rectF =
        RectF(rectInset, rectInset, clipRectRight - rectInset, clipRectBottom - rectInset)

    // DONE: Step 6.0 Override onDraw() and call a function for each shape you are drawing.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
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
        canvas?.apply {
            // DONE: Step 15.0
            //  a. Save the canvas.
            //  b. Translate to the text row and second column position.
            save()
            translate(columnTwo, textRow)
            // DONE: Step 15.1 change paint
            //  . Set color GREEN
            //  . Align the RIGHT side of the text with the origin
            paint.color = Color.GREEN
            paint.textAlign = Paint.Align.LEFT
            // DONE: Step 15.2 draw the text
            drawText(context.getString(R.string.translated), clipRectLeft, clipRectTop, paint)
            // DONE: Step 15.3 Then restore the canvas to its previous state.
            restore()
        }
    }

    private fun drawSkewedTextExample(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 16.0
            //  a. Save the canvas.
            //  b. Translate to the text row and second column position.
            save()
            translate(columnTwo, textRow)
            // DONE: Step 16.1 change paint
            //  . Set color YELLOW
            //  . Align the LEFT side of the text with the origin
            paint.color = Color.YELLOW
            paint.textAlign = Paint.Align.RIGHT
            skew(0.2f, 0.3f)
            drawText(
                context.getString(R.string.skewed),
                clipRectLeft, clipRectTop, paint
            )
            // DONE: Step 16.2 Then restore the canvas to its previous state.
            restore()
        }
    }

    private fun drawOutsideClippingExample(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 14.0
            //  a. Save the canvas.
            //  b. Translate to the fourth row and first column position.
            save()
            translate(columnOne, rowFour)
            // DONE: Step 14.1 clip the double rectInset rectangle
            clipRect(
                2 * rectInset,
                2 * rectInset,
                clipRectRight - 2 * rectInset,
                clipRectBottom - 2 * rectInset
            )
            // DONE: Step 14.2
            //  c. Draw by calling drawClippedRectangle().
            //  d. Then restore the canvas to its previous state.
            drawClippedRectangle(canvas)
            restore()
        }
    }

    private fun drawRoundedRectangleClippingExample(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 13.1
            //  a. Save the canvas.
            //  b. Translate to the third row and second column position.
            save()
            translate(columnTwo, rowThree)
            // DONE: Step 13.2 Clears any lines and curves from the path
            path.rewind()
            // DONE: Step 13.3 the rounded rect
            path.addRoundRect(
                rectF,
                clipRectRight / 4,
                clipRectRight / 4,
                Path.Direction.CCW
            )
            // DONE: Step 13.4 Clip the combined path from the canvas
            clipPath(path)
            // DONE: Step 13.5
            //  c. Draw by calling drawClippedRectangle().
            //  d. Then restore the canvas to its previous state.
            drawClippedRectangle(canvas)
            restore()
        }
    }

    private fun drawCombinedClippingExample(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 12.0
            //  a. Save the canvas.
            //  b. Translate to the third row and first column position.
            save()
            translate(columnOne, rowThree)
            // DONE: Step 12.1 Clears any lines and curves from the path
            path.rewind()
            // DONE: Step 12.2 Create the circle path
            path.addCircle(
                clipRectLeft + rectInset + circleRadius,
                clipRectTop + circleRadius + rectInset,
                circleRadius,
                Path.Direction.CCW
            )
            // DONE: Step 12.2 Create the rectangle path
            path.addRect(
                clipRectRight / 2 - circleRadius,
                clipRectTop + circleRadius + rectInset,
                clipRectRight / 2 + circleRadius,
                clipRectBottom - rectInset, Path.Direction.CCW
            )
            // DONE: Step 12.3 Clip the combined path from the canvas
            clipPath(path)
            // DONE: Step 12.4
            //  c. Draw by calling drawClippedRectangle().
            //  d. Then restore the canvas to its previous state.
            drawClippedRectangle(canvas)
            restore()
        }
    }

    private fun drawIntersectionClippingExample(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 11.0
            //  a. Save the canvas.
            //  b. Translate to the second row and column position.
            save()
            translate(columnTwo, rowTwo)
            // DONE: Step 11.1 Clip the first frame.
            clipRect(
                clipRectLeft, clipRectTop,
                clipRectRight - smallRectOffset,
                clipRectBottom - smallRectOffset
            )
            // DONE: Step 11.2 Clip the second frame resulting on the same frames in commom.
            // The method clipRect(float, float, float, float, Region.Op
            // .INTERSECT) was deprecated in API level 26. The recommended
            // alternative method is clipRect(float, float, float, float), which
            // is currently available in API level 26 and higher.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                clipRect(
                    clipRectLeft + smallRectOffset,
                    clipRectTop + smallRectOffset,
                    clipRectRight,
                    clipRectBottom,
                    Region.Op.INTERSECT
                )
            } else {
                clipRect(
                    clipRectLeft + smallRectOffset,
                    clipRectTop + smallRectOffset,
                    clipRectRight,
                    clipRectBottom
                )
            }
            // DONE: Step 11.3
            //  c. Draw by calling drawClippedRectangle().
            //  d. Then restore the canvas to its previous state.
            drawClippedRectangle(canvas)
            restore()
        }
    }

    private fun drawCircularClippingExample(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 10.0
            //  a. Save the canvas.
            //  b. Translate to the second row and first column position.
            save()
            translate(columnOne, rowTwo)
            // DONE: Step 10.1 Clears any lines and curves from the path
            //  but unlike reset(), keeps the internal data structure for faster reuse
            path.rewind()
            // DONE: Step 10.2 Create the circle path
            path.addCircle(
                circleRadius,
                clipRectBottom - circleRadius,
                circleRadius,
                Path.Direction.CCW
            )
            // DONE: Step 10.3 Remove the circle from draw
            // The method clipPath(path, Region.Op.DIFFERENCE) was deprecated in
            // API level 26. The recommended alternative method is
            // clipOutPath(Path), which is currently available in
            // API level 26 and higher.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                clipPath(path, Region.Op.DIFFERENCE)
            } else {
                clipOutPath(path)
            }
            // DONE: Step 10.4
            //  c. Draw by calling drawClippedRectangle().
            //  d. Then restore the canvas to its previous state.
            drawClippedRectangle(canvas)
            restore()
        }

    }

    private fun drawDifferenceClippingExample(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 9.0
            //  a. Save the canvas.
            //  b. Translate to the first row and second column position.
            save()
            translate(columnTwo, rowOne)
            // DONE: Step 9.1 Use the subtraction of two clipping rectangles to create a frame.
            clipRect(
                2 * rectInset,
                2 * rectInset,
                clipRectRight - 2 * rectInset,
                clipRectBottom - 2 * rectInset
            )
            // The method clipRect(float, float, float, float, Region.Op
            // .DIFFERENCE) was deprecated in API level 26. The recommended
            // alternative method is clipOutRect(float, float, float, float),
            // which is currently available in API level 26 and higher.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                clipRect(
                    4 * rectInset,
                    4 * rectInset,
                    clipRectRight - 4 * rectInset,
                    clipRectBottom - 4 * rectInset, Region.Op.DIFFERENCE
                )
            } else {
                clipOutRect(
                    4 * rectInset,
                    4 * rectInset,
                    clipRectRight - 4 * rectInset,
                    clipRectBottom - 4 * rectInset
                )
            }

            // DONE: Step 9.2
            //  c. Draw by calling drawClippedRectangle().
            //  d. Then restore the canvas to its previous state.
            drawClippedRectangle(canvas)
            restore()
        }
    }

    private fun drawBackAndUnclippedRectangle(canvas: Canvas?) {
        canvas?.apply {
            // DONE: Step 8.0 Implement the drawBackAndUnclippedRectangle() method.
            //  a. Save the canvas.
            //  b. Translate to the first row and column position.
            //  c. Draw by calling drawClippedRectangle().
            //  d. Then restore the canvas to its previous state.
            drawColor(Color.GRAY)
            save()
            translate(columnOne, rowOne)
            drawClippedRectangle(canvas)
            restore()
        }
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
