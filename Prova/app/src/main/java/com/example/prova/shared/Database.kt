package com.example.prova.shared

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prova.client.Client
import com.example.prova.client.ClientRepository
import com.example.prova.computer.Computer
import com.example.prova.computer.ComputerRepository

@Database(entities = [Client::class, Computer::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun clientRepository(): ClientRepository
    abstract fun computerRepository(): ComputerRepository
}