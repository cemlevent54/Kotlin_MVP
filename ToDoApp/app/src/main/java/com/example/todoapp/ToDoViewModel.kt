package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.db.ToDoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class ToDoViewModel(private val todoDao: ToDoDao) : ViewModel() {

    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()

    fun addTodo(title: String) {
        if (title.isBlank()) return // Boş string eklenmesini önle
        viewModelScope.launch(Dispatchers.IO) {
            val newTodo = Todo(title = title, createdAt = Date(System.currentTimeMillis()))
            todoDao.addTodo(newTodo)
        }
    }

    fun removeTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id) // ✅ Direkt silme işlemi yap
        }
    }
}
