package com.example.prova.client

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClientRepository {
    @Insert
    suspend fun insert(client: Client)

    @Query("SELECT * FROM client c")
    suspend fun findAll(): List<Client>

    @Query("SELECT * FROM client c WHERE c.cpf LIKE ('%' || :cpf || '%')")
    suspend fun findAllByCpf(cpf: String): List<Client>

    @Query("SELECT * FROM client c WHERE c.cpf = :cpf")
    suspend fun findByCpf(cpf: String): Client?

    @Update
    suspend fun update(client: Client)

    @Delete
    suspend fun delete(client: Client)
}