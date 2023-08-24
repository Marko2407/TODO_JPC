package com.mvukosav.todo_jpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mvukosav.todo_jpc.ui.addedittodo.AddEditTodoScreen
import com.mvukosav.todo_jpc.ui.theme.TODO_JPCTheme
import com.mvukosav.todo_jpc.ui.todolist.TodoListScreen
import com.mvukosav.todo_jpc.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODO_JPCTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST) {
                    composable(Routes.TODO_LIST) {
                        TodoListScreen(onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                    composable(
                        Routes.ADD_EDIT_TODO + "?todoId={todoId}", arguments = listOf(
                            navArgument("todoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                    ) {
                        AddEditTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }

                }
            }
        }
    }
}
