package com.example.android.minipaint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import androidx.core.content.res.ResourcesCompat

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
    }
}