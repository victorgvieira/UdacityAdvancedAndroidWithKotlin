package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test

import org.junit.Assert.*

class StatisticsUtilsTest {

    // DONE Step 2: Write Your First Test
    //  If there's no completed task and one active task,
    //  then there are 100% percent active tasks and 0% completed tasks
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsZeroHundred() {
        // DONE Step 2.1 Create an active tasks (the false makes this active)
        val tasks = listOf(
                Task(title = "title", description = "description", isCompleted = false)
        )

        // DONE Step 2.2 Call our function
        val result = getActiveAndCompletedStats(tasks)

        // DONE Step 2.3 Check the result
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }

    // DONE Step 3.1: Start by writing test for when you have a normal task list:
    //  If there are two completed tests and three active test, the completed percentage should be 40f and the active percentage should be 60f.
    @Test
    fun getActiveAndCompletedStats_completedAndActivated_returnsFortySexty() {
        // DONE Step 3.2 Create the task list
        val tasks = listOf(
                Task(title = "title", description = "description", isCompleted = true),
                Task(title = "title", description = "description", isCompleted = true),
                Task(title = "title", description = "description", isCompleted = false),
                Task(title = "title", description = "description", isCompleted = false),
                Task(title = "title", description = "description", isCompleted = false)
        )

        // DONE Step 3.3 Call our function
        val result = getActiveAndCompletedStats(tasks)

        // DONE Step 3.4 Check the result
        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }

    // DONE Step 4: Write some tests for the buggy edge cases
    // DONE Step 4.1: If there is an empty list (emptyList()), then both percentages should be 0f.
    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        val tasks = emptyList<Task>()

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    // DONE Step 4.2: If there was an error loading tasks, the list will be null, then both percentages should be 0f.
    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        val tasks = null

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
    // DONE Step 4.3 run the tests to confirm these tests fail.
    // NOTE When following TDD, you'll write the tests first and then update the actual code.
}