package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.runBlocking

// DONE Step 6.1: Define an object called ServiceLocator.
object ServiceLocator {
    // DONE Step 6.2: Create database and repository instance variables and set both to null.
    private var database: ToDoDatabase? = null
    // DONE Step 6.3: Annotate the repository with @Volatile because it could get used by multiple threads (@Volatile is explained in detail here).
    @Volatile
    var tasksRepository: TasksRepository? = null
        //DONE Step 8.2: Mark the setter for tasksRepository as @VisibleForTesting.
        @VisibleForTesting set

    //DONE Step 8.3: Add an instance variable called lock with the Any value:
    private val lock = Any()

    // DONE Step 6.4 create the function provideTasksRepository
    //  - Either provides an already existing repository or creates a new one.
    //  This method should be synchronized on this to avoid, in situations with multiple threads running, ever accidentally creating two repository instances.
    fun provideTasksRepository(context: Context): TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)
        }
    }

    // DONE Step 6.5 createTasksRepository - Code for creating a new repository.
    //  Will call createTaskLocalDataSource and create a new TasksRemoteDataSource.
    private fun createTasksRepository(context: Context): TasksRepository {
        val newRepo = DefaultTasksRepository(TasksRemoteDataSource, createTaskLocalDataSource(context))
        tasksRepository = newRepo
        return newRepo
    }

    // DONE Step 6.6 createTaskLocalDataSource - Code for creating a new local data source. Will call createDataBase.
    private fun createTaskLocalDataSource(context: Context): TasksDataSource {
        val tmpDatabase = database ?: createDataBase(context)
        return TasksLocalDataSource(tmpDatabase.taskDao())

    }

    // DONE Step 6.7 createDataBase - Code for creating a new database.
    private fun createDataBase(context: Context): ToDoDatabase {
        val result = Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java,
                "Tasks.db")
                .build()
        database = result
        return result
    }

    //DONE Step 8.4: Add a testing specific method called resetRepository
    // which clears out the database and sets both the repository and database to null
    @VisibleForTesting
    fun resetRepository() = synchronized(lock) {
        runBlocking {
            TasksRemoteDataSource.deleteAllTasks()
        }
        // Clear all data to avoid test pollution.
        database?.apply {
            clearAllTables()
            close()
        }
        tasksRepository = null
        database = null
    }

}