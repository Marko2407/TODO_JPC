package com.mvukosav.todo_jpc.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(todoId: Int): Todo?

    fun getTodos(): Flow<List<Todo>>
}