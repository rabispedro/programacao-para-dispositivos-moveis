package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Pedido
import com.example.a0xc0ffee.model.mapper.Mapper
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PedidoController(override val mapper: Mapper<Pedido>) : BaseController<Pedido>("Pedido") {
    suspend fun cadastrar(pedido: Pedido): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(pedido.id)
            .set(mapper.toMap(pedido))
            .addOnSuccessListener {
                Log.d("debug", "Pedido inserido com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
                result = false
            }
            .await()

        return result
    }

    suspend fun listar(callback: (List<Pedido>) -> Unit) {
        repository
            .collection(collection)
            .orderBy("cliente.cpf", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val entities = it.map{ entity -> mapper.fromMap(entity.data) }
                callback(entities)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()
    }

    suspend fun listar(texto: String): List<Pedido> {
        val result: MutableList<Pedido> = mutableListOf()

        repository
            .collection(collection)
            .whereEqualTo("cliente.cpf", texto)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    result.add(mapper.fromMap(obj.data))
                }
            }
            .await()

        return result
    }
}