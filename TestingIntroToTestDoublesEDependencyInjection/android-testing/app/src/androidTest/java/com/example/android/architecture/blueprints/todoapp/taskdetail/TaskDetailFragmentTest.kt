package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test
import org.junit.runner.RunWith

// DONE Step 5.2: Add annotation for @MediumTest(<1000ms) and @RunWith AndroidJUnit4
@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    // DONE Step 5.3: Create a method test called activeTaskDetails_DisplayedInUi
    @Test
    fun activeTaskDetails_DisplayedInUi() {
        //GIVEN
        // DONE Step 5.4: Create a task.
        val activeTask = Task("Active Task", "AndroidX Rocks", false)
        // DONE Step 5.5: Create a Bundle, which represents the fragment arguments for the task that get passed into the fragment
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        // DONE Step 5.6: The launchFragmentInContainer function creates a FragmentScenario, with this bundle and a theme.
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
        // DONE Step 5.7: Set this thread to wait an amount of time to see the test running
        Thread.sleep(2000)
    }
}