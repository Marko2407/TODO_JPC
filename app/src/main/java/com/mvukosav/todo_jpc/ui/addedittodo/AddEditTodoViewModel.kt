package com.mvukosav.todo_jpc.ui.addedittodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvukosav.todo_jpc.data.Todo
import com.mvukosav.todo_jpc.data.TodoRepository
import com.mvukosav.todo_jpc.ui.todolist.TodoListEvent
import com.mvukosav.todo_jpc.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    this@AddEditTodoViewModel.todo = todo
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.OnDescriptionChanged -> description = event.description
            is AddEditEvent.OnTitleChanged -> title = event.title
            is AddEditEvent.OnSaveTodoClicked -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUIEvent(UIEvent.ShowSnackbar(message = "The title can't be empty!"))
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )
                    sendUIEvent(UIEvent.PopBackStack)
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
