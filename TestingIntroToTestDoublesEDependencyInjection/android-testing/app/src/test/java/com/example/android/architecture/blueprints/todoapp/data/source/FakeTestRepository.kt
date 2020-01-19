package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

class FakeTestRepository : TasksRepository {
    // DONE Step 3.2 Add a LinkedHashMap variable representing the current list of tasks
    private var taskServiceData: LinkedHashMap<String, Task> = LinkedHashMap()
    // DONE Step 3.3 Add a MutableLiveData for your observable tasks
    private val observableTasks = MutableLiveData<Result<List<Task>>>()

    //DONE Step 3.4 implements getTasks
    // - This method should just take the tasksServiceData and turn it into a list using tasksServiceData.values.toList()
    // and then return that as a Success result.
    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        return Result.Success(taskServiceData.values.toList())
    }

    //DONE Step 3.5 implements refreshTasks - Updates the value of observableTasks to be what is returned by getTasks().
    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    //DONE Step 3.6 implements observeTasks - Create a coroutine using runBlocking and run refreshTasks, then return observableTasks.
    override fun observeTasks(): LiveData<Result<List<Task>>> {
        runBlocking {
            refreshTasks()
        }
        return observableTasks
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun completeTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun completeTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun activateTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun activateTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clearCompletedTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // DONE Step 3.7: Add the addTasks method, which takes in a vararg of tasks,
    //  adds each to the HashMap and then refreshes the tasks:
    fun addTasks(vararg tasks: Task) {
        tasks.forEach {
            taskServiceData[it.id] = it
        }
        runBlocking { refreshTasks() }
    }
}