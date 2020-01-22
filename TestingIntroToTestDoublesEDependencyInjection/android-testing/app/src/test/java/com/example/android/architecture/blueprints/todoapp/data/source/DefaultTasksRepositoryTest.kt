package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DefaultTasksRepositoryTest {
    // DONE Step 2.5: At the top of your new DefaultTasksRepositoryTest class,
    //  add Tasks and List<Tasks> as member variables to represent the data in your local and remote fake data sources
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task2).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    // DONE Step 2.6: Also create three variables - two FakeDataSource member variables
    //  (one for each data source for your repository) and a variable for the DefaultTasksRepository which you will test:
    private lateinit var localDataSource: FakeDataSource
    private lateinit var remoteDataSource: TasksDataSource
    private lateinit var taskRepository: DefaultTasksRepository

    // DONE Step 2.7: Create a method called createRepository and annotate it with @Before.
    @Before
    fun createRepository() {
        // DONE Step 2.8: Instantiate your fake data sources, using the remoteTasks and localTasks lists.
        localDataSource = FakeDataSource(localTasks.toMutableList())
        remoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        // DONE Step 2.9: Instantiate your tasksRepository, using the two fake data sources you just created and Dispatchers.Unconfined
        taskRepository = DefaultTasksRepository(
                remoteDataSource,
                localDataSource,
                // NOTE: Dispatchers.Unconfined should be replaced with Dispatchers.Main
                //  this requires understanding more about coroutines + testing
                //  so we will keep this as Unconfined for now.
                Dispatchers.Unconfined)
    }

    // DONE Step 2.10: Write a test for the repository's getTasks method.
    //  Check that when you call get tasks with true (meaning that it should reload from the remote data source)
    //  that it returns data from the remote data source (as opposed to the local data source):
    // DONE Step 2.12 add runBlockingTest so that it takes in your entire test as a "block" of code
    //  - Add the @ExperimentalCoroutinesApi above the method
    // NOTE: use runBlockingTest in your test classes when you're calling a suspend function.
    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = runBlockingTest {
        //WHEN
        val tasks = taskRepository.getTasks(true) as Result.Success

        //THEN
        assertThat(tasks.data, IsEqual(remoteTasks))
    }


}