package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //DONE: Step 1.0 replace the default content view
        // and set the content view to a new instance of ClippedView.
        // This will be your custom view for the clipping examples.
        setContentView(ClippedView(this))
    }
}
