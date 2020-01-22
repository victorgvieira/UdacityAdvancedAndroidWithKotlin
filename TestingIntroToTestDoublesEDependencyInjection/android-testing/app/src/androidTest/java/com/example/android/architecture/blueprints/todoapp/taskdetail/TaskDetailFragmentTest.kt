package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// DONE Step 5.2: Add annotation for @MediumTest(<1000ms) and @RunWith AndroidJUnit4
@MediumTest
@RunWith(AndroidJUnit4::class)
// DONE Step 8.10: Annotate the whole class with @ExperimentalCoroutinesApi.
@ExperimentalCoroutinesApi
class TaskDetailFragmentTest {

    //  DONE Step 8.5: Declare a lateinit TasksRepository variable.
    private lateinit var tasksRepository: TasksRepository

    //  DONE Step 8.6: Add a setup method which sets up a FakeAndroidTestRepository before each test
    @Before
    fun initRepository() {
        tasksRepository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = tasksRepository
    }

    //  DONE Step 8.7: Add a teardown method which cleans a FakeAndroidTestRepository after each test
    @After
    fun cleanupDb() {
        ServiceLocator.resetRepository()
    }

    //  DONE Step 5.3: Create a method test called activeTaskDetails_DisplayedInUi
    //  DONE Step 8.8: Wrap the function body of activeTaskDetails_DisplayedInUi() in runBlockingTest.
    @Test
    fun activeTaskDetails_DisplayedInUi() = runBlockingTest {
        //GIVEN
        // DONE Step 5.4: Create a task.
        val activeTask = Task("Active Task", "AndroidX Rocks", false)
        //  DONE Step 8.9: Save activeTask in the repository before launching the fragment.
        tasksRepository.saveTask(activeTask)
        // DONE Step 5.5: Create a Bundle, which represents the fragment arguments for the task that get passed into the fragment
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        // DONE Step 5.6: The launchFragmentInContainer function creates a FragmentScenario, with this bundle and a theme.
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
        // DONE Step 5.7: Set this thread to wait an amount of time to see the test running
        Thread.sleep(2000)
    }
}