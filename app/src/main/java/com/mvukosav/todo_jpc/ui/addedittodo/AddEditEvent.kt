package com.mvukosav.todo_jpc.ui.addedittodo

sealed class AddEditEvent {
    data class OnTitleChanged(val title: String) : AddEditEvent()
    data class OnDescriptionChanged(val description: String) : AddEditEvent()
    object OnSaveTodoClicked : AddEditEvent()
}
