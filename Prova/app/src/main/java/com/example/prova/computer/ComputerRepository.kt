package com.example.prova.computer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ComputerRepository {
    @Insert
    suspend fun insert(computer: Computer)

    @Transaction
    @Query("SELECT * FROM client c")
    suspend fun findAllByClient(): List<ComputerByClient>

    @Query("SELECT * FROM computer c")
    suspend fun findAll(): List<Computer>

    @Query("SELECT * FROM client c WHERE c.cpf LIKE ('%' || :cpf || '%')")
    suspend fun findAllByCpf(cpf: String): List<ComputerByClient>

    @Query("SELECT * FROM computer c WHERE c.id = :id")
    suspend fun findById(id: String): Computer?

    @Update
    suspend fun update(computer: Computer)

    @Delete
    suspend fun delete(computer: Computer)
}
