package com.mvukosav.todo_jpc.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {

    override suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    override suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo)

    override suspend fun getTodoById(todoId: Int): Todo? = dao.getTodoById(todoId)

    override fun getTodos(): Flow<List<Todo>> = dao.getTodos()
}