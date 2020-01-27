/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.firebaseui_login_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
// DONE Step 1.0: Create a Firebase project
//  - In the Firebase console, click Add project, then select or enter a Project name. You can name your project anything, but try to pick a name relevant to the app you’re building
//  - Click Continue
//  - You can skip setting up Google Analytics and chose the Not Right Now option.
//  - Click Create Project to finish setting up the Firebase project

// DONE Step 1.1: Register your app with Firebase
//  - In the center of the Firebase console's project overview page, click the Android icon to launch the setup workflow.
//  - Enter your app's application ID in the Android package name field.
//      - Make sure you enter the ID your app is using, otherwise you cannot add or modify this value after you’ve registered your app with your Firebase project.
//      - An application ID is sometimes referred to as a package name.
//      - Find this application ID in your module (app-level) Gradle file, usually app/build.gradle (example ID: com.yourcompany.yourproject).
//  - Enter the debug signing certificate SHA-1. You can generate this key by entering the following command in your command line terminal
//      - "keytool -alias androiddebugkey -keystore ~/.android/debug.keystore -list -v -storepass android"
//  - Click to Register app.

// DONE Step 1.2: Add the Firebase configuration file to your project
//  - Click Download google-services.json to obtain your Firebase Android config file (google-services.json).
//      - You can download your Firebase Android config file again at any time.
//      - Make sure the config file is not appended with additional characters and should only be named google-services.json
//  - Move your config file into the module (app-level) directory of your app.


// DONE Step 1.3: Configure your Android project to enable Firebase products
//  To enable Firebase products in your app, add the google-services plugin to your Gradle files.
//  - In your project-level Gradle file (build.gradle), add rules to include the Google Services plugin.
//      - add inside the key "dependencies", the line
//          - "classpath 'com.google.gms:google-services:4.3.0'"  // Google Services plugin
//  - In your module (app-level) build.gradle file
//      - at the bottom of the file, add the line
//          - "apply plugin: 'com.google.gms.google-services'"  // Google Play services Gradle plugin

// NOTE So far you’ve set up your app so that it can communicate with Firebase,
//  but now you need to add a specific Firebase library that enables you to implement login.

// DONE Step 1.4: Add the Firebase dependency
//  The firebase-auth SDK allows management of authenticated users of your application
//      - Add the following dependency in your build.gradle (Module:app)
//          - "implementation 'com.firebaseui:firebase-ui-auth:5.0.0'"

// DONE Step 1.5: Sync your project with gradle files.
