package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.mapper.Mapper
import kotlinx.coroutines.tasks.await

class ClienteController(override val mapper: Mapper<Cliente>) : BaseController<Cliente>("Cliente") {
    suspend fun cadastrar(cliente: Cliente, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(cliente.cpf.value)
            .set(mapper.toMap(cliente))
            .addOnSuccessListener {
                val result = Pair(true, "Cliente inserido com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao inserir cliente")
                callback(result)
            }
            .await()
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
                callback(listOf())
            }
            .await()
    }

    suspend fun listar(texto: String, callback: (List<Cliente>) -> Unit) {
        repository
            .collection(collection)
            .whereEqualTo("cpf", texto)
            .get()
            .addOnSuccessListener {
                val entities = it.map { entity -> mapper.fromMap(entity.data) }
                callback(entities)
            }
            .addOnFailureListener {
                callback(listOf())
            }
            .await()
    }

    suspend fun deletar(cpf: String, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(cpf)
            .delete()
            .addOnSuccessListener {
                val result = Pair(true, "Cliente inserido com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao inserir cliente")
                callback(result)
            }
            .await()
    }

    suspend fun alterar(cliente: Cliente, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(cliente.cpf.value)
            .update(mapper.toMap(cliente))
            .addOnSuccessListener {
                val result = Pair(true, "Cliente alterado com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao alterar o cliente")
                callback(result)
            }
            .await()
    }
}