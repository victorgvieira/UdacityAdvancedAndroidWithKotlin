package com.example.android.minipaint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*DONE Step: 3.1 delete setContentView*/
//        setContentView(R.layout.activity_main)
        /*DONE Step: 3.2 create instance of MyCanvasView*/
        val myCanvasView = MyCanvasView(this)
        /*DONE Step: 3.3 request the full scree for th layout of myCanvasView
        *   Setting SYSTEM_UI_FLAG_FULLSCREEN flag
        *   In thi way, the view completely fills the screen*/
        myCanvasView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        /*DONE Step: 3.4 add the contentDescription from strings.xml*/
        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)
        /*DONE Step: 3.5 set the content view to myCanvasView*/
        /*Note: You will need to know the size of the view for drawing, but you cannot get the size
            of the view in the onCreate() method, because the size has not been determined at this point.*/
        setContentView(myCanvasView)
    }
}
