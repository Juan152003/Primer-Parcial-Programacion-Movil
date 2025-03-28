package com.example.aplicacionprimerparcialjdas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionprimerparcialjdas.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask.asStateFlow()

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val newTask = Task(
                id = _tasks.value.size + 1,
                title = title,
                description = description
            )
            _tasks.value = _tasks.value + newTask
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            _tasks.value = _tasks.value.map {
                if (it.id == task.id) it.copy(isCompleted = !it.isCompleted)
                else it
            }
        }
    }

    fun selectTask(task: Task) {
        viewModelScope.launch {
            _selectedTask.value = task
        }
    }

    fun clearSelectedTask() {
        viewModelScope.launch {
            _selectedTask.value = null
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _tasks.value = _tasks.value.filter { it.id != task.id }
            if (_selectedTask.value?.id == task.id) {
                clearSelectedTask()
            }
        }
    }

    fun editTask(task: Task, newTitle: String, newDescription: String) {
        viewModelScope.launch {
            _tasks.value = _tasks.value.map {
                if (it.id == task.id) it.copy(
                    title = newTitle,
                    description = newDescription
                )
                else it
            }
            _selectedTask.value = _tasks.value.find { it.id == task.id }
        }
    }
} 