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
package com.example.android.architecture.blueprints.todoapp.data.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Result.Success
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//DONE Step 1: Create the FakeDataSource Class which will be a test double of a LocalDataSource and RemoteDataSource.
//  - In the test source set, right click select New -> Package.
//  - Make a data package with a source package inside.
//  - Create a new class called FakeDataSource in the data/source package:

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 */
// DONE Step 2.0: Change the DefaultTaskRepository's constructor,
//  from taking in an Application to taking in both data sources and the coroutine dispatcher
//  also remove the private visibility
// DONE Step 2.4
//  Right click on the DefaultTasksRepository class name and select Generate then Test.
//  Follow the prompts to create DefaultTasksRepositoryTest in the test source set.

// DONE Step 3.0:  right click on the class name.
//  - Then select Refactor -> Extract -> Interface:
//  - Extract to separate file:
//  - In the Extract Interface window, change the interface name to TasksRepository.
//  - In the Members to form interface section, check all members except the two companion members and the private methods
//  - Click Refactor. The new TasksRepository interface should appear in the data/source package

// DONE Step 3.1: In the test source set, in data/source create the kotlin class FakeTestRepository.kt.
//  - Extend from the TasksRepository interface:
//  - Hover over the error until you see the suggestion menu, then click and select Implement members:
//  - Select all of the methods and press OK
class DefaultTasksRepository constructor(
        private val tasksRemoteDataSource: TasksDataSource,
        private val tasksLocalDataSource: TasksDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : TasksRepository {


    // DONE Step 2.2: Delete the old instance variables. You're defining them in the constructor:
//    private val tasksRemoteDataSource: TasksDataSource
//    private val tasksLocalDataSource: TasksDataSource
//    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    // DONE Step 7.1: delete the companion object
//    companion object {
//        @Volatile
//        private var INSTANCE: DefaultTasksRepository? = null
//
//        // DONE Step 2.3: Update the getRepository method to use the new constructor:
//        fun getRepository(app: Application): DefaultTasksRepository {
//            return INSTANCE ?: synchronized(this) {
//                val database = Room.databaseBuilder(app,
//                        ToDoDatabase::class.java, "Tasks.db")
//                        .build()
//                DefaultTasksRepository(TasksRemoteDataSource, TasksLocalDataSource(database.taskDao())).also {
//                    INSTANCE = it
//                }
//            }
//        }
//    }

    // DONE Step 2.1: Because we passed the dependencies in, remove the init method.
    //  You no longer need to create the dependencies.
//    init {
//        val database = Room.databaseBuilder(application.applicationContext,
//                ToDoDatabase::class.java, "Tasks.db")
//                .build()
//
//        tasksRemoteDataSource = TasksRemoteDataSource
//        tasksLocalDataSource = TasksLocalDataSource(database.taskDao())
//    }

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        if (forceUpdate) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return tasksLocalDataSource.getTasks()
    }

    override suspend fun refreshTasks() {
        updateTasksFromRemoteDataSource()
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return tasksLocalDataSource.observeTasks()
    }

    override suspend fun refreshTask(taskId: String) {
        updateTaskFromRemoteDataSource(taskId)
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        val remoteTasks = tasksRemoteDataSource.getTasks()

        if (remoteTasks is Success) {
            // Real apps might want to do a proper sync.
            tasksLocalDataSource.deleteAllTasks()
            remoteTasks.data.forEach { task ->
                tasksLocalDataSource.saveTask(task)
            }
        } else if (remoteTasks is Result.Error) {
            throw remoteTasks.exception
        }
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        return tasksLocalDataSource.observeTask(taskId)
    }

    private suspend fun updateTaskFromRemoteDataSource(taskId: String) {
        val remoteTask = tasksRemoteDataSource.getTask(taskId)

        if (remoteTask is Success) {
            tasksLocalDataSource.saveTask(remoteTask.data)
        }
    }

    /**
     * Relies on [getTasks] to fetch data and picks the task with the same ID.
     */
    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        if (forceUpdate) {
            updateTaskFromRemoteDataSource(taskId)
        }
        return tasksLocalDataSource.getTask(taskId)
    }

    override suspend fun saveTask(task: Task) {
        coroutineScope {
            launch { tasksRemoteDataSource.saveTask(task) }
            launch { tasksLocalDataSource.saveTask(task) }
        }
    }

    override suspend fun completeTask(task: Task) {
        coroutineScope {
            launch { tasksRemoteDataSource.completeTask(task) }
            launch { tasksLocalDataSource.completeTask(task) }
        }
    }

    override suspend fun completeTask(taskId: String) {
        withContext(ioDispatcher) {
            (getTaskWithId(taskId) as? Success)?.let { it ->
                completeTask(it.data)
            }
        }
    }

    override suspend fun activateTask(task: Task) = withContext<Unit>(ioDispatcher) {
        coroutineScope {
            launch { tasksRemoteDataSource.activateTask(task) }
            launch { tasksLocalDataSource.activateTask(task) }
        }
    }

    override suspend fun activateTask(taskId: String) {
        withContext(ioDispatcher) {
            (getTaskWithId(taskId) as? Success)?.let { it ->
                activateTask(it.data)
            }
        }
    }

    override suspend fun clearCompletedTasks() {
        coroutineScope {
            launch { tasksRemoteDataSource.clearCompletedTasks() }
            launch { tasksLocalDataSource.clearCompletedTasks() }
        }
    }

    override suspend fun deleteAllTasks() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { tasksRemoteDataSource.deleteAllTasks() }
                launch { tasksLocalDataSource.deleteAllTasks() }
            }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        coroutineScope {
            launch { tasksRemoteDataSource.deleteTask(taskId) }
            launch { tasksLocalDataSource.deleteTask(taskId) }
        }
    }

    private suspend fun getTaskWithId(id: String): Result<Task> {
        return tasksLocalDataSource.getTask(id)
    }
}
