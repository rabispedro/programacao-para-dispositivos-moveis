package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Pedido
import com.example.a0xc0ffee.model.mapper.Mapper
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PedidoController(override val mapper: Mapper<Pedido>) : BaseController<Pedido>("Pedido") {
    suspend fun cadastrar(pedido: Pedido, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(pedido.id)
            .set(mapper.toMap(pedido))
            .addOnSuccessListener {
                val result = Pair(true, "Pedido inserido com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao inserir pedido")
                callback(result)
            }
            .await()
    }

    suspend fun listar(callback: (List<Pedido>) -> Unit) {
        repository
            .collection(collection)
            .orderBy("cliente.cpf", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val result = it.map{ entity -> mapper.fromMap(entity.data) }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()
    }

    suspend fun listar(texto: String, callback: (List<Pedido>) -> Unit) {
        repository
            .collection(collection)
            .whereEqualTo("cliente.cpf", texto)
            .get()
            .addOnSuccessListener {
                val result = it.map { entity -> mapper.fromMap(entity.data) }
                callback(result)
            }
            .addOnFailureListener {
                callback(listOf())
            }
            .await()
    }
}