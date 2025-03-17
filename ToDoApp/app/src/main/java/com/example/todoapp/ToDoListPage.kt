package com.example.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import np.com.bimalkafle.todoapp.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ToDoListPage(viewModel: ToDoViewModel) {
    // val todoList = getFakeTodo()
    val todoList by viewModel.todoList.observeAsState(emptyList()) // LiveData'dan observe ediyoruz
    var inputText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Enter task") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
                onClick = {
                    viewModel.addTodo(inputText)
                    inputText = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, //  Butonun arka planı sarı
                    contentColor = Color.Black // Yazı rengi siyah (kontrast için)
                )
            ) {
                Text(text = "Add")
            }
        }

        if (todoList.isEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = "No items yet",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,

            )
        } else {
            LazyColumn {
                itemsIndexed(todoList) { _, todo ->
                    ToDoItem(item=todo, onDelete = {
                        viewModel.removeTodo(todo.id)
                    })
                }
            }
        }

    }
}



@Composable
fun ToDoItem(item: Todo, onDelete: ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)) // Önce clip()
            .background(Color.Black) // Sonra background()
            .padding(16.dp), // İç boşluk ekleyelim
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (modifier = Modifier.weight(1f)){
            Text(
                text = SimpleDateFormat("HH:mm:ss dd/MM", Locale.ENGLISH).format(item.createdAt), // Doğru tarih biçimi
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 10.sp

            )
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp
            )
        }
        IconButton(onClick = onDelete ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}
