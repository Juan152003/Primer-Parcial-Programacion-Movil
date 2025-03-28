package com.example.aplicacionprimerparcialjdas.navigation

sealed class NavRoutes(val route: String) {
    object TaskList : NavRoutes("taskList")
    object TaskDetail : NavRoutes("taskDetail/{taskId}") {
        fun createRoute(taskId: Int) = "taskDetail/$taskId"
    }
    object AddTask : NavRoutes("addTask")
} 