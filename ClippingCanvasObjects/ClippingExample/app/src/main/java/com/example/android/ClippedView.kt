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

}
