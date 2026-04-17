package com.example.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room.person.Person
import com.example.room.person.PersonRepository

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personRepository(): PersonRepository
}