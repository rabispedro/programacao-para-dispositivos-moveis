package com.example.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.room.person.PersonController
import com.example.room.person.PersonService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

        val personRepository = database.personRepository()
        val personService = PersonService(personRepository)
        val personController = PersonController(personService)

        setContent {
            personController.SimplePersonView()
        }
    }
}


