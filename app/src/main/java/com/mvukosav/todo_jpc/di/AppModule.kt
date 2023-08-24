package com.mvukosav.todo_jpc.di

import android.app.Application
import androidx.room.Room
import com.mvukosav.todo_jpc.data.TodoDatabase
import com.mvukosav.todo_jpc.data.TodoRepository
import com.mvukosav.todo_jpc.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(app: Application) =
        Room.databaseBuilder(app, TodoDatabase::class.java, "todo_db").build()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDatabase: TodoDatabase): TodoRepository =
        TodoRepositoryImpl(todoDatabase.dao)
}