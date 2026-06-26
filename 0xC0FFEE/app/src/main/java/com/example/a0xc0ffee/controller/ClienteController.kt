package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.mapper.Mapper
import kotlinx.coroutines.tasks.await

class ClienteController(override val mapper: Mapper<Cliente>) : BaseController<Cliente>("Cliente") {
    suspend fun cadastrar(cliente: Cliente): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(cliente.cpf.value)
            .set(mapper.toMap(cliente))
            .addOnSuccessListener {
                Log.d("debug", "Cliente inserido com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
                result = false
            }
            .await()

        return result
    }

    suspend fun listar(callback: (List<Cliente>) -> Unit) {
        repository
            .collection(collection)
            .get()
            .addOnSuccessListener {
                val entities = it.map { entity -> mapper.fromMap(entity.data) }
                callback(entities)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()
    }

    suspend fun listar(texto: String): List<Cliente> {
        val result: MutableList<Cliente> = mutableListOf()

        repository
            .collection(collection)
            .whereEqualTo("cpf", texto)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    val entity = mapper.fromMap(obj.data)
                    result.add(entity)
                }
            }
            .await()

        return result
    }

    suspend fun deletar(cpf: String): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(cpf)
            .delete()
            .addOnSuccessListener {
                Log.d("debug", "Cliente inserido com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }

    suspend fun alterar(cliente: Cliente): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(cliente.cpf.value)
            .update(mapper.toMap(cliente))
            .addOnSuccessListener {
                Log.d("debug", "Cliente alterado com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }
}