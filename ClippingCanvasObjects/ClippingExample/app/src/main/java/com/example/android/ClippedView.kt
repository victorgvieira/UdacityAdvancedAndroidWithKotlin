package com.example.android

import android.content.Context
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

}
