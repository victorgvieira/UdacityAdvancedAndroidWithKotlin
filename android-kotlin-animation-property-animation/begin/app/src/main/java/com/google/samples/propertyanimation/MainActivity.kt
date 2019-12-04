/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.propertyanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    lateinit var star: ImageView
    lateinit var rotateButton: Button
    lateinit var translateButton: Button
    lateinit var scaleButton: Button
    lateinit var fadeButton: Button
    lateinit var colorizeButton: Button
    lateinit var showerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        star = findViewById(R.id.star)
        rotateButton = findViewById<Button>(R.id.rotateButton)
        translateButton = findViewById<Button>(R.id.translateButton)
        scaleButton = findViewById<Button>(R.id.scaleButton)
        fadeButton = findViewById<Button>(R.id.fadeButton)
        colorizeButton = findViewById<Button>(R.id.colorizeButton)
        showerButton = findViewById<Button>(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }

    private fun rotater() {
        // DONE Step 1.0: create an animation using ObjectAnimator.ofFloat
        //  that rotates the ImageView containing the star from a value of -360 to 0.
        //  This means that the view, and thus the star inside it, will rotate in a full circle (360 degrees) around its center.
        val starAnimator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)
        // DONE Step 1.1: Change the duration property of the animator to 1000 milliseconds
        starAnimator.duration = 1000
        // DONE Step 1.2 add a new AnimatorListenerAdapter object to the animator
        //  and override the onAnimationStart() and onAnimationEnd()
        starAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                // DONE Step 1.3 disable rotate button when animation start
                rotateButton.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                // DONE Step 1.4 enable rotate button when animation end
                rotateButton.isEnabled = true
            }
        })
        //DONE Step 1.5: Start the animation
        starAnimator.start()
    }

    private fun translater() {
    }

    private fun scaler() {
    }

    private fun fader() {
    }

    private fun colorizer() {
    }

    private fun shower() {
    }

}
