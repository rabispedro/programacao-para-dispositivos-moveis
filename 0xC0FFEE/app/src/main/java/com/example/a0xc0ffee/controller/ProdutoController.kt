package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Produto
import com.example.a0xc0ffee.model.mapper.ClienteMapper
import com.example.a0xc0ffee.model.mapper.Mapper
import kotlinx.coroutines.tasks.await

class ProdutoController(override val mapper: Mapper<Produto>) : BaseController<Produto>("Produto") {
    suspend fun cadastrar(produto: Produto, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(produto.id)
            .set(mapper.toMap(produto))
            .addOnSuccessListener {
                val result = Pair(true, "Produto inserido com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao inserir produto")
                callback(result)
            }
            .await()
    }

    suspend fun listar(callback: (List<Produto>) -> Unit) {
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

    suspend fun listar(texto: String, callback: (List<Produto>) -> Unit) {
        repository
            .collection(collection)
            .get()
            .addOnSuccessListener {
                val result = it
                    .filter { (it.data["id"] as String).contains(texto) }
                    .map { entity -> mapper.fromMap(entity.data) }
                callback(result)
            }
            .addOnFailureListener {
                callback(listOf())
            }
            .await()
    }

    suspend fun listarClientes(callback: (List<Cliente>) -> Unit) {
        repository
            .collection("Cliente")
            .get()
            .addOnSuccessListener {
                val result = it.map{ entity -> ClienteMapper.fromMap(entity.data) }
                callback(result)
            }
            .addOnFailureListener {
                callback(listOf())
            }
            .await()
    }

    suspend fun deletar(id: String, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(id)
            .delete()
            .addOnSuccessListener {
                val result = Pair(true, "Produto deletado com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao deletar produto")
                callback(result)
            }
            .await()
    }

    suspend fun alterar(produto: Produto, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(produto.id)
            .update(mapper.toMap(produto))
            .addOnSuccessListener {
                val result = Pair(true, "Produto alterado com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao alterar produto")
                callback(result)
            }
            .await()
    }
}
