package com.example.android.minipaint

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs

// DONE Step: 6.0 define a constant for the stroke width
private const val STROKE_WIDTH = 12f // has to be float

// DONE Step: 2.0 create the file MyCanvasView
// DONE Step: 2.1 make MyCanvasView class extends View class passing the context
// DONE Step: 2.2 follow sugested imports
class MyCanvasView(context: Context) : View(context) {
    // These are your bitmap and canvas for caching what has been drawn before
    // DONE Step: 4.0 define member variable for a bitmap called extraBitmap
    private lateinit var extraBitmap: Bitmap
    // DONE Step: 4.1 define member variable for a canvas called extraCanvas
    private lateinit var extraCanvas: Canvas

    // DONE Step: 4.2 define a class level variable backgroundColor, for the background color of the canvas
    //  and initialize it to the R.color.colorBackground
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

    // DONE Step: 6.1 define a variable drawColor for holding the color to draw with
    //  and initialize it with the colorPaint R.color.colorPaint
    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

    // DONE Step: 6.2 add a variable paint for a Paint object and initialize it with given code
    //  Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        //  Smooths out edges of what is draw without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE  // default: FILL
        strokeJoin = Paint.Join.ROUND   // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    // DONE Step: 7.0 add a variable path and initialize it with a Path object
    //  to store the path that is being drawn when following the user's touch on the screen.
    //  Import android.graphics.Path for the Path.
    private val path = Path()

    //  DONE Step: 8.1 add the motionTouchEventX and motionTouchEventY variables
    //   for caching the x and y coordinates of the current touch event (the MotionEvent coordinates).
    //   Initialize them to 0f.
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    // DONE Step: 9.0 add variables to cache the latest x and y values.
    //  After the user stops moving and lifts their touch,
    //  these are the starting point for the next path (the next segment of the line to draw).
    private var currentY = 0f
    private var currentX = 0f

    // DONE Step: 10.0 add a touchTolerance variable and set it to ViewConfiguration.get(context).scaledTouchSlop.
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    // DONE Step: 12.0 add a variable called frame that holds a Rect object.
    private lateinit var frame: Rect

    // DONE Step: 4.3 override the onSizeChanged() method.
    //  This callback method is called by the Android system with the changed screen dimensions,
    //  that is, with a new width and height (to change to) and the old width and height (to change from).
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        /*NOTE: keep super call*/
        super.onSizeChanged(w, h, oldw, oldh)

        // DONE Step: 4.7 a new bitmap and canvas are created every time the function executes.
        //  You need a new bitmap, because the size has changed.
        //  However, this is a memory leak, leaving the old bitmaps around.
        //  To fix this, recycle extraBitmap before creating the next one
        // NOTE: the "::" operator, in this case, make the isInitialized method to refer the lateinit property
        // There's no such method in Bitmap class
        // More about :: operator at https://kotlinlang.org/docs/reference/reflection.html
        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        // DONE Step: 4.4 create an instance of Bitmap with the new width and height,
        //  which are the screen size, and assign it to extraBitmap.
        //  The third argument is the bitmap color configuration.
        //  ARGB_8888 stores each color in 4 bytes and is recommended
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        // DONE Step: 4.5 create a Canvas instance from extraBitmap and assign it to extraCanvas.
        extraCanvas = Canvas(extraBitmap)
        // DONE Step: 4.6 call drawColor to specify the background color in which to fill extraCanvas
        extraCanvas.drawColor(backgroundColor)

        // DONE Step: 12.1 create the Rect that will be used for the frame,
        //  using the new dimensions and the inset
        val inset = 40
        frame = Rect(inset, inset, width - inset, height - inset)

    }

    //  DONE Step: 5.0 Override onDraw() and draw the contents of the cached extraBitmap on the canvas associated with the view.
    //   The drawBitmap() Canvas method comes in several versions.
    //   In this code, you provide the bitmap, the x and y coordinates (in pixels) of the top left corner,
    //   and null for the Paint, as you'll set that later.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //  Note: The 2D coordinate system used for drawing on a Canvas is in pixels,
        //  and the origin (0,0) is at the top left corner of the Canvas.
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
        canvas?.drawRect(frame, paint)
    }

    //  DONE Step: 8.0 override the onTouchEvent() method
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // NOTE: Remove super
//        return super.onTouchEvent(event)
        // NOTE: Since event could be null, lets call nullable check only one time
        event?.also {
            // DONE Step: 8.2 cache the x and y coordinates of the passed in event.
            motionTouchEventX = it.x
            motionTouchEventY = it.y

            // DONE Step: 8.3 use a when expression to handle motion events for touching down on the screen,
            //   moving on the screen, and releasing touch on the screen.
            //   These are the events of interest for drawing a line on the screen.
            //   For each event type, call a utility method
            // See the MotionEvent class documentation for a full list of touch events
            when (it.action) {
                MotionEvent.ACTION_DOWN -> touchStart()
                MotionEvent.ACTION_MOVE -> touchMove()
                MotionEvent.ACTION_UP -> touchUp()
            }
        }
        return true
    }

    // DONE Step: 8.4 Create stubs for the three functions touchStart(), touchMove(), and touchUp().
    private fun touchUp() {
        // DONE Step: 11.0 Reset the path so it doesn't get drawn again.
        path.reset()
    }

    private fun touchMove() {
        // DONE Step: 10.1 Calculate the traveled distance (dx, dy)
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)

        // DONE Step: 10.2 check if the moved distance is above the touchTolerance value
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // DONE Step: 10.3 create a curve between the two points and store it in path
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            // DONE Step: 10.4 update the running currentX and currentY tally
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // DONE Step: 10.5 and draw the path
            // Draw the path in the extra bitmap to cache it.
            extraCanvas.drawPath(path, paint)
        }
        // DONE Step: 10.6 call invalidate() to force redrawing of the screen with the updated path
        invalidate()

        /*NOTES:    Calculate the distance that has been moved (dx, dy).
                    If the movement was further than the touch tolerance, add a segment to the path.
                    Set the starting point for the next segment to the endpoint of this segment.
                    Using quadTo() instead of lineTo() create a smoothly drawn line without corners. See Bezier Curves.
                    Call invalidate() to (eventually call onDraw() and) redraw the view.*/
    }

    // DONE Step: 9.1 implement the touchStart()
    //  Reset the path, move to the x-y coordinates of the touch event (motionTouchEventX and motionTouchEventY)
    //  and assign currentX and currentY to that value.
    // NOTE: Called when the user first touch the screen
    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }
}

