package com.example.room.person

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PersonRepository {
    @Insert
    suspend fun insert(person: Person)

    @Query("SELECT * FROM person p WHERE p.cpf LIKE ('%' || :cpf || '%')")
    suspend fun findByCpf(cpf: String): Person?

    @Query("SELECT * FROM person p")
    suspend fun findAll(): List<Person>

    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Transaction
    suspend fun insertAndQuery(person: Person): Person? {
        if (findByCpf(person.cpf) != null) {
            insert(person)
        }
        return findByCpf(person.cpf)
    }
}

