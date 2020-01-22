package com.example.android.architecture.blueprints.todoapp.tasks

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.TodoApplication
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

//  DONE Step 11.2: Copy the provided code on the lesson for this class
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class TasksFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    //  DONE Step 11.3: Add the test clickTask_navigateToDetailFragmentOne running on runBlockingTest
    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest {
        //  DONE Step 11.4: create two tasks, one active and one complete, give id = id1 and id2
        repository.saveTask(Task("TITLE1", "DESCRIPTION1", false, "id1"))
        repository.saveTask(Task("TITLE2", "DESCRIPTION2", true, "id2"))

        //  DONE Step 11.5: initiate TasksFragment using FragmentScenario and store in a variable
        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)

        //  DONE Step 11.6: Use Mockito's mock function to create a mock of NavController
        val navController = mock(NavController::class.java)

        //  DONE Step 11.7: Make your new mock the fragment's NavController
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        //  DONE Step 11.8: Add the code to click on the item in the RecyclerView that has the text "TITLE1":
        onView(withId(R.id.tasks_list))
                .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                        hasDescendant(withText("TITLE1")), click()))

        //  DONE Step 11.9: Verify that navigate was called, with the correct argument
        // THEN - Verify that we navigate to the first detail screen
        verify(navController).navigate(TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment("id1"))
    }

    //  DONE Step 12: Optional
    //   To see if you can write a navigation test yourself, try this:
    //    Write the test clickAddTaskButton_navigateToAddEditFragment
    //    which checks that if you click on the + FAB, you navigate to the AddEditTaskFragment.
    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() = runBlockingTest {
        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        onView(withId(R.id.add_task_fab)).perform(click())

        // THEN - Verify that we navigate to the AddEditTaskFragment
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                        null,
                        ApplicationProvider.getApplicationContext<TodoApplication>().getString(R.string.add_task)))

    }


}