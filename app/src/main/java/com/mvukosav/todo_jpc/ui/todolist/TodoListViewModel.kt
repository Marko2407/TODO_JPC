package com.mvukosav.todo_jpc.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvukosav.todo_jpc.data.Todo
import com.mvukosav.todo_jpc.data.TodoRepository
import com.mvukosav.todo_jpc.utils.Routes
import com.mvukosav.todo_jpc.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    val todos = repository.getTodos()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null
    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnDeleteTodoClicked -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                }

                sendUIEvent(UIEvent.ShowSnackbar("Todo deleted", "Undo"))
            }

            is TodoListEvent.OnAddTodoClicked -> sendUIEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO))
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }

            is TodoListEvent.OnTodoClicked -> sendUIEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            is TodoListEvent.OnUndoDeleteClicked -> {
                deletedTodo?.let {
                    viewModelScope.launch {
                        repository.insertTodo(it)
                    }
                }
            }
        }
    }

    private fun sendUIEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}