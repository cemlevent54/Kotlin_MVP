package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.db.ToDoDao
import com.example.todoapp.db.ToDoDatabase
import com.example.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ Veritabanını başlat ve DAO al
        val database = ToDoDatabase.getDatabase(applicationContext)
        val dao = database.getToDoDao() // DAO'yu al

        // ✅ ViewModel'i ToDoDao ile başlat
        val toDoViewModel = ViewModelProvider(
            this,
            ToDoViewModelFactory(dao) // Burada DAO parametre olarak veriliyor
        )[ToDoViewModel::class.java]

        setContent {
            ToDoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding), viewModel = toDoViewModel)
                }
            }
        }
    }
}

class ToDoViewModelFactory(private val todoDao: ToDoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: ToDoViewModel? = null) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        AppTitle() // 🔹 Başlık bileşeni
        viewModel?.let {
            ToDoListPage(viewModel = it) // Eğer ViewModel varsa listeyi göster
        }
    }
}

// 🔹 Yeni başlık bileşeni
@Composable
fun AppTitle(modifier: Modifier = Modifier) {
    Text(
        text = "ToDo Deneme Uygulaması",
        modifier = modifier
    )
}

// ✅ Preview için ViewModel olmadan çalışabilir hale getirildi
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        MainScreen() // ViewModel olmadan bile çalışabilir
    }
}
