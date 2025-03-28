package com.example.aplicacionprimerparcialjdas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aplicacionprimerparcialjdas.ui.TaskViewModel
import com.example.aplicacionprimerparcialjdas.ui.screens.AddTaskScreen
import com.example.aplicacionprimerparcialjdas.ui.screens.TaskDetailScreen
import com.example.aplicacionprimerparcialjdas.ui.screens.TaskListScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: TaskViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.TaskList.route
    ) {
        composable(NavRoutes.TaskList.route) {
            TaskListScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        
        composable(NavRoutes.AddTask.route) {
            AddTaskScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        
        composable(
            route = NavRoutes.TaskDetail.route,
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable
            val task = viewModel.tasks.value.find { it.id == taskId }
            task?.let {
                viewModel.selectTask(it)
                TaskDetailScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
} 