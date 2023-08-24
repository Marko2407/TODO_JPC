package com.mvukosav.todo_jpc.ui.todolist

import com.mvukosav.todo_jpc.data.Todo

sealed class TodoListEvent {
    data class OnDeleteTodoClicked(val todo: Todo) : TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean) : TodoListEvent()
    data class OnTodoClicked(val todo: Todo) : TodoListEvent()
    object OnUndoDeleteClicked : TodoListEvent()
    object OnAddTodoClicked : TodoListEvent()
}

