package com.example.android.customfancontroller

//  DONE: Step 1.6 Create a new Kotlin class called DialView
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.content.withStyledAttributes
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

//  DONE: Step 1.9 Above the DialView class definition, just below the imports, add a top-level enum to represent the available fan speeds
//      OFF, LOW, MEDIUM, HIGH with respective values from R.string.fan_*
//  Note that this enum is of type Int because the values are string resources rather than actual strings.
private enum class FanSpeed(val label: Int) {
    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high);

    //DONE: Step 3.0 add an extension function next() that changes the current fan speed
    //  to the next speed in the list (from OFF to LOW, MEDIUM, and HIGH, and then back to OFF
    fun next() = when (this) {
        OFF -> LOW
        LOW -> MEDIUM
        MEDIUM -> HIGH
        HIGH -> OFF
    }
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
    //      Import android.graphics.PointF if requested.
    // NOTE: These values are created and initialized here instead of when the view is actually draw
    // to ensure that the actual drawing step runs as fast as possible.
    // Radius of the circle. Set when the view is run on the screen
    private var radius = 0.0f
    // The active selection. the current speed of the fan
    private var fanSpeed = FanSpeed.OFF
    // position variable which will be used to draw label and indicator circle position
    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    //  DONE: Step 1.12 initialize a Paint object with a handful of basic styles.
    //      Import android.graphics.Paint and android.graphics.Typeface when requested
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    // DONE: Step 4.3 declare variables to cache the attribute values
    private var fanSpeedLowColor = 0
    private var fanSpeedMediumColor = 0
    private var fanSpeedMaxColor = 0


    // DONE: Step 3.1 add an init() block.
    //  Setting the view's isClickable property to true enables that view to accept user input
    init {
        isClickable = true

        // DONE: Step 4.4 add the following code using the withStyledAttributes extension function.
        //  You supply the attributes and view, and set your local variables
        context.withStyledAttributes(attrs, R.styleable.DialView) {
            fanSpeedLowColor = getColor(R.styleable.DialView_fanColor1, 0)
            fanSpeedMediumColor = getColor(R.styleable.DialView_fanColor2, 0)
            fanSpeedMaxColor = getColor(R.styleable.DialView_fanColor3, 0)
        }
    }

    // DONE: Step 3.2 override the performClick() with given code
    override fun performClick(): Boolean {
        // if this method was handled by any custom onClickListener method, then return
        // NOTE: super.performClick() must happen first, which enables accessibility events as well as calls onClickListener()
        if (super.performClick()) return true

        fanSpeed = fanSpeed.next()
        // set view content description for Accessibility
        contentDescription = resources.getString(fanSpeed.label)
        // invalidate to redraw the view
        invalidate()
        // Tell that this method was handled
        return true

    }

    // DONE: Step 2.0 override the onSizeChanged() method to calculate the size for the custom view's dial.
    //  Import kotlin.math.min when requested.
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // remove the super call
//        super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    // DONE: Step 2.1 add the code to define a computeXYForSpeed() extension function for the PointF class.
    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float) {

        // DONE: Step 2.2 add the code to calculates the X, Y coordinates on the screen for the text label and current indicator (0, 1, 2, or 3),
        //  given the current FanSpeed position and radius of the dial. You'll use this in onDraw().
        //  Import kotlin.math.cos and kotlin.math.sin when requested.
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    // DONE: Step 2.3 override onDraw() method to render the view on the screen with the Canvas and Paint classes
    //    Import android.graphics.Canvas when requested.
    override fun onDraw(canvas: Canvas?) {
        // keep the super call
        super.onDraw(canvas)
        canvas?.apply {
            // DONE: Step 2.4 set the paint color to gray (Color.GRAY) or green (Color.GREEN)
            //  depending on whether the fan speed is OFF or any other value.
            //  Import android.graphics.Color when requested.
            // this code is not needed in step 4.5 and thereafter
//            paint.color = if (fanSpeed == FanSpeed.OFF) Color.GRAY else Color.GREEN

            // DONE: Step 4.5 et the dial color based on the current fan speed
            paint.color = when (fanSpeed) {
                FanSpeed.OFF -> Color.GRAY
                FanSpeed.LOW -> fanSpeedLowColor
                FanSpeed.MEDIUM -> fanSpeedMediumColor
                FanSpeed.HIGH -> fanSpeedMaxColor
            }

            // DONE: Step 2.5 call drawCircle() to draw the circle for the dial
            // This method uses the current view width and height to find the center of the circle, the radius of the circle, and the current paint color.
            // The width and height properties are members of the View superclass and indicate the current dimensions of the view.
            /*STEP USING LESSON CODE*/
            // drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
            /*STEP USING CUSTOM CODE*/
            drawDialCircle()

            // DONE: Step 2.6 call drawCircle() to draw the circle for the fan speed indicator mark
            // This part uses the PointF.computeXYforSpeed() extension method to calculate the X,Y coordinates
            // for the indicator center based on the current fan speed.
            /*STEP USING LESSON CODE*/
//            val markerRadius = radius + RADIUS_OFFSET_INDICATOR
//            pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
//            paint.color = Color.BLACK
//            drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)
            /*STEP USING CUSTOM CODE*/
            drawFanSpeedCircle()

            // DONE: Step 2.7 draw the fan speed labels (0, 1, 2, 3) around the dial using drawText()
            //  we call PointF.computeXYForSpeed() again to get the position for each label,
            // and reuses the pointPosition object each time to avoid allocations
            /*STEP USING LESSON CODE*/
//            val labelRadius = radius + RADIUS_OFFSET_LABEL
//            for (fanSpeed in FanSpeed.values()) {
//                pointPosition.computeXYForSpeed(fanSpeed, labelRadius)
//                val label = resources.getString(fanSpeed.label)
//                drawText(label, pointPosition.x, pointPosition.y, paint)
//            }
            /*STEP USING CUSTOM CODE*/
            drawFanSpeedLabels()
        }
    }

    /* For purpose of learning were created extended functions for drawing on canvas*/
    private fun Canvas.drawDialCircle() {
        drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
    }

    private fun Canvas.drawFanSpeedCircle() {
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
        paint.color = Color.BLACK
        drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)
    }

    private fun Canvas.drawFanSpeedLabels() {
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (fanSpeed in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(fanSpeed, labelRadius)
            val label = resources.getString(fanSpeed.label)
            drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }
}