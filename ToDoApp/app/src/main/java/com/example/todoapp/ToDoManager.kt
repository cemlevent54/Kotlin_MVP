package com.example.todoapp

import java.time.Instant
import java.util.Date

object ToDoManager {
    private val todoList = mutableListOf<Todo>()

    fun getAllTodo() : List<Todo> {
        return todoList.toList()
    }

    fun addTodo(title : String) {
        synchronized(this) { // Çoklu thread kullanımında veri kaybını önler
            val newTodo = Todo(
                id = System.currentTimeMillis().toInt(), // Benzersiz ID oluşturur
                title = title,
                createdAt = Date(System.currentTimeMillis()) // Daha güvenli tarih oluşturma
            )
            todoList.add(newTodo)
        }
    }

    fun removeTodo(id : Int) {
        synchronized(this) {
            todoList.removeIf { it.id == id }
        }
    }
}