package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val title: String,
    val createdAt: Date
)

//fun getFakeTodo(): List<Todo> {
//    return listOf(
//        Todo(1, "Buy milk", Date.from(Instant.now())),
//        Todo(2, "Buy bread", Date.from(Instant.now())),
//        Todo(3, "Buy cheese", Date.from(Instant.now())),
//        Todo(4, "Buy butter", Date.from(Instant.now()))
//    )
//}
