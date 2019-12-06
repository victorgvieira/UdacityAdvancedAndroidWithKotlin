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
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat


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
        // This time, the property isn’t an android.util.Property object,
        // but is instead a property exposed via a setter, View.setBackgroundColor(int).
        // Since you cannot refer to an android.util.Property object directly,
        // like you did before with ALPHA, etc.,
        // you will use the approach of passing in the name of the property as a String
        // DONE Step 5.0: create an animation using ObjectAnimator.onInt
        //  passing the property name as "backgroundColor".
        //NOT USED in Step 5.0.1 and thereafter
        //Animating between two integer values does not necessarily yield the same result
        // as animating between the colors that those two integers represent
//        val animator = ObjectAnimator.ofInt(star.parent, "backgroundColor", Color.BLACK, Color.RED)
        // DONE Step 5.0.1: create an animation using ObjectAnimator.ofArgb
        //  passing the property name as "backgroundColor".
        // We need an animator that knows how to interpret (and animate between) color values,
        // rather than simply the integers that represent those color
        val animator = ObjectAnimator.ofArgb(star.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.apply {
            duration = 500
            // DONE Step 5.1: Set the repeatCount property on the animation to 1
            repeatCount = 1
            // DONE Step 5.2: Set the repeatMode as REVERSE for repeating again from the same values
            repeatMode = ObjectAnimator.REVERSE
            //DONE Step 5.3 call disableViewDuringAnimation method
            disableViewDuringAnimation(colorizeButton)
        }
        // DONE Step 5.4 Starts
        animator.start()
    }

    private fun shower() {
        // DONE Step 6.0 get a reference to the star field ViewGroup
        //  (which is just the parent of the current star view).
        val container = star.parent as ViewGroup
        // DONE Step 6.1 get the width and height of that container
        //  (which you will use to calculate the end translation values for our falling stars)
        val containerH = container.height
        val containerW = container.width
        // DONE Step 6.2 set the default width and height of our star
        //  (which you will later alter with a scale factor to get different-sized stars)
        var starH = star.height.toFloat()
        var starW = star.width.toFloat()
        // DONE Step 6.3 Create a new View to hold the star graphic.
        //  Because the star is a VectorDrawable asset, use an AppCompatImageView,
        //  which has the ability to host that kind of resource
        val newStar = AppCompatImageView(this).apply {
            setImageResource(R.drawable.ic_star)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            // DONE Step 6.4 Set the size of the star.
            //  Modify the star to have a random size, from .1x (10%) to 1.6x (160%) of its default size.
            scaleX = Math.random().toFloat() * 1.5f + .1f
            scaleY = scaleX
            // DONE Step 6.5 Use this scale factor to change the cached width/height values,
            //  because you will need to know the actual pixel height/width for later calculations
            starW *= scaleX
            starH *= scaleY

            // DONE Step 6.6 Position the new star. Horizontally, it should appear randomly somewhere from the left edge to the right edge.
            //  This code uses the width of the star to position it from half-way off the screen on the left (-starW / 2) to half-way off the screen on the right
            //  (with the star positioned at (containerW - starW / 2)
            translationX = Math.random().toFloat() * (containerW - starW / 2)
        }
        // DONE Step 6.7 add it to the background container
        container.addView(newStar)
        // DONE Step 6.8 Specifically, create the falling animation and add an AccelerateInterpolator,
        //  it will use an accelerating motion (simulating gravity pulling the star downward at a constantly faster rate.
        //  The star will animates from -starH to (containerH + starH), which effectively places it just off the container at the top
        //  and moves it until it’s just outside the container at the bottom
        val mover =
            ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)
        // DONE Step 6.9 create the rotation animation and add a LinearInterpolator,
        //  it will use a smooth linear motion (moving at a constant rate over the entire rotation animation).
        //  The star will rotate a random amount between 0 and 1080 degrees (three times around
        val rotator =
            ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        // DONE Step 6.10 Create the AnimatorSet
        val set = AnimatorSet().apply {
            // DONE Step 6.11 add the child animators to it (along with information to play them in parallel).
            playTogether(mover, rotator)
            // DONE Step 6.12 The default animation time of 300 milliseconds is too quick to enjoy the falling stars,
            //  so set the duration to a random number between 500 and 2000 milliseconds, so stars fall at different speeds
            duration = (Math.random() * 1500 + 500).toLong()

            // DONE Step 6.13 Set a simple listener to wait for the end of the animation and remove it
            //  Once newStar has fallen off the bottom of the screen, it should be removed from the container.
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    container.removeView(newStar)
                }
            })
        }
        // DONE Step 6.14 Starts
        set.start()
        // NOTE: you didn’t have to disable the button during the animation, as you did in the earlier tasks,
        // because this time we wanted to create several simultaneous animations.
        // There was no problem with discontinuous motion artifacts because each animation is independent of the others and operates on a different target object
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
