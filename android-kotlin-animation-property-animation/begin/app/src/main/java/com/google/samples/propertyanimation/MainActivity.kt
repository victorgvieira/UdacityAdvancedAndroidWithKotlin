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

import android.animation.*
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
        // NOT USED in Step 2.3 and thereafter
//        starAnimator.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationStart(animation: Animator?) {
//                // DONE Step 1.3 disable rotate button when animation start
//                rotateButton.isEnabled = false
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                // DONE Step 1.4 enable rotate button when animation end
//                rotateButton.isEnabled = true
//            }
//        })
        //DONE Step 2.5 call disableViewDuringAnimation method in rotater()
        // to disable their buttons during their respective animations
        //NOT USED in Step 2.6 and thereafter
//        disableViewDuringAnimation(rotateButton, starAnimator)
        //DONE Step 2.8 Modify the code in rotater() to call the extension function
        starAnimator.disableViewDuringAnimation(rotateButton)
        //DONE Step 1.5: Start the animation
        starAnimator.start()
    }

    private fun translater() {
        // DONE Step 2.0: create an animation using ObjectAnimator.ofFloat
        //  that moves the star to the right by 200 pixels.
        val animator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 200f)
        animator.apply {
            // DONE Step 2.1: Set the repeatCount property on the animation to 1
            repeatCount = 1
            // DONE Step 2.2: Set the repeatMode as REVERSE for repeating again from the same values
            repeatMode = ObjectAnimator.REVERSE
        }
        //DONE Step 2.4 call disableViewDuringAnimation method in translater()
        // to disable their buttons during their respective animations
        //NOT USED in Step 2.6 and thereafter
//        disableViewDuringAnimation(translateButton, animator)
        //DONE Step 2.7 Modify the code in translater() to call the extension function
        animator.disableViewDuringAnimation(translateButton)
        animator.start()
    }

    private fun scaler() {
        // Note: There is no single property that scales in both the x and y dimensions,
        // so animations that scale in both x and y need to animate both of these separate properties in parallel.
        // DONE Step 3.0: create two a PropertyValuesHolder for SCALE_X
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        // DONE Step 3.1: create two a PropertyValuesHolder for SCALE_Y
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        // DONE Step 3.2: create an ObjectAnimator object, using the scaleX and scaleY objects
        val animator = ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY)
        animator.apply {
            // DONE Step 3.3: Set the repeatCount property on the animation to 1
            repeatCount = 1
            // DONE Step 3.4: Set the repeatMode as REVERSE for repeating again from the same values
            repeatMode = ObjectAnimator.REVERSE
            // DONE Step 3.5: call the disableViewDurationAnimation() extension function
            //  to disable scaleButton during the animation
            disableViewDuringAnimation(scaleButton)
        }
        // DONE Step 3.6 Starts
        animator.start()
    }

    private fun fader() {
        // DONE Step 4.0: create an animation using ObjectAnimator.ofFloat
        //  that change the star opacity to the 0.
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0f)
        animator.apply {
            // DONE Step 4.1: Set the repeatCount property on the animation to 1
            repeatCount = 1
            // DONE Step 4.2: Set the repeatMode as REVERSE for repeating again from the same values
            repeatMode = ObjectAnimator.REVERSE
            //DONE Step 4.3 call disableViewDuringAnimation method
            disableViewDuringAnimation(fadeButton)
        }
        // DONE Step 4.4 Starts
        animator.start()
    }

    private fun colorizer() {
    }

    private fun shower() {
    }


    //DONE Step 2.3: Create a function called disableViewDuringAnimation(),
    // which takes a View and an ObjectAnimator,
    // and use the code you already wrote earlier in rotater() to create the body of this function
    fun disableViewDuringAnimation(view: View, animator: ObjectAnimator) {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })

    }

    //DONE Step 2.6 create the method disableViewDuringAnimation as an extension function of ObjectAnimator
    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

}
