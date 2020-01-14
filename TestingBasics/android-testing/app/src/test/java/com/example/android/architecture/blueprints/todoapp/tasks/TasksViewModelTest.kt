package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

// DONE Step 6.5 Add the AndoirdJUnit4 test runner:
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    // DONE Step 6.1: Create a new test called addNewTask_setsNewTaskEvent:
    @Test
    fun addNewTask_setsNewTaskEvent() {
        // Given a fresh TasksViewModel
        // DONE Step 6.3 Create a TasksViewModel using ApplicationProvider.getApplicationContext() from the AndroidX test library
        val taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())


        // When adding a new task
        // DONE Step 6.4 add a new task
        taskViewModel.addNewTask()


        // Then the new task event is triggered
    }
}