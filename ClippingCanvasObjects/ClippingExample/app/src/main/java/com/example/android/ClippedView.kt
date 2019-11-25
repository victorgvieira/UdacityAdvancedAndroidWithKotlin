package com.example.android

import android.content.Context
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


}
