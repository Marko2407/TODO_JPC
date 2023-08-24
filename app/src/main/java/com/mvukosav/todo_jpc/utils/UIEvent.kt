package com.mvukosav.todo_jpc.utils

sealed class UIEvent {
    object PopBackStack : UIEvent()

    data class Navigate(val route: String) : UIEvent()

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UIEvent()
}