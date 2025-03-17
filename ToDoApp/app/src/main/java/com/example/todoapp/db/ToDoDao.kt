package com.example.todoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.Todo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_table ORDER BY createdAt DESC") // 🔹 Yeni eklenenleri önce göster
    fun getAllTodo(): LiveData<List<Todo>>

    @Insert
    suspend fun addTodo(todo: Todo)

    @Query("DELETE FROM todo_table WHERE id = :id") // 🔹 Küçük harfli tablo adı kullanıldı
    suspend fun deleteTodo(id: Int)
}
