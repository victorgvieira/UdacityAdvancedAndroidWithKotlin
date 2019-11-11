package com.example.android.customfancontroller

//  DONE: Step 1.6 Create a new Kotlin class called DialView
import android.content.Context
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

//  DONE: Step 1.9 Above the DialView class definition, just below the imports, add a top-level enum to represent the available fan speeds
// OFF, LOW, MEDIUM, HIGH with respective values from R.string.fan_*
//  Note that this enum is of type Int because the values are string resources rather than actual strings.
private enum class FanSpeed(val label: Int) {
    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high);
}

//  DONE: Step 1.10 add constants RADIUS_OFFSET_LABEL = 30 and RADIUS_OFFSET_INDICATOR = -35. You'll use these as part of drawing the dial indicators and labels
private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

//  DONE: Step 1.7 Modify the class definition to extend View. Import android.view.View when prompted
//  DONE: Step 1.8 Click on View and then click the red bulb. Choose Add Android View constructors using '@JvmOverloads'
//  @JvmOverloads instructs the Kotling compiler to generate overloads for this function that substitute default parameter values
class DialView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //  DONE: Step 1.11 Define several private variables you need in order to draw the custom view.
    //  Import android.graphics.PointF if requested.
    // NOTE: These values are created and initialized here instead of when the view is actually drawn
    // to ensure that the actual drawing step runs as fast as possible.
    // Radius of the circle. Set when the view is run on the screen
    private var radius = 0.0f
    // The active selection. the current speed of the fan
    private var fanSpeed = FanSpeed.OFF
    // position variable which will be used to draw label and indicator circle position
    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    //  DONE: Step 1.12 initialize a Paint object with a handful of basic styles.
    //  Import android.graphics.Paint and android.graphics.Typeface when requested
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }
}