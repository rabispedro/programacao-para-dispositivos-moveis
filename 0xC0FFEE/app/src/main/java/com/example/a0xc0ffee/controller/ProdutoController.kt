package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Produto
import com.example.a0xc0ffee.model.mapper.ClienteMapper
import com.example.a0xc0ffee.model.mapper.Mapper
import kotlinx.coroutines.tasks.await

class ProdutoController(override val mapper: Mapper<Produto>) : BaseController<Produto>("Produto") {
    suspend fun cadastrar(produto: Produto): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(produto.id)
            .set(mapper.toMap(produto))
            .addOnSuccessListener {
                Log.d("debug", "Produto inserido com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
                result = false
            }
            .await()

        return result
    }

    suspend fun listar(): List<Produto> {
        val result: MutableList<Produto> = mutableListOf()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    val entity = mapper.fromMap(obj.data)
                    result.add(entity)
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }

    suspend fun listar(nome: String): List<Produto> {
        val result: MutableList<Produto> = mutableListOf()

        repository
            .collection(collection)
            .whereEqualTo("id", nome)
            .whereEqualTo("tipoDoGrao", nome)
            .whereEqualTo("pontoDaTorra", nome)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    result.add(mapper.fromMap(obj.data))
                }
            }
            .await()

        Log.d("debug", "Result:")
        result.forEach { Log.d("debug", "$it") }

        return result
    }

    suspend fun listarClientes(): List<Cliente> {
        val result: MutableList<Cliente> = mutableListOf()

        repository
            .collection("Cliente")
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    Log.d("debug", "Object: ${obj.data}")
//                    val objeto = cliente.toObject(Cliente::class.java)
                    val cliente = ClienteMapper.fromMap(obj.data)
                    Log.d("debug", "Document: $cliente")
                    result.add(cliente)
                }
            }
            .await()

        Log.d("debug", "Result:")
        result.forEach { Log.d("debug", "$it") }

        return result
    }

    suspend fun buscar(id: String): Produto {
        var result: Produto = Produto("", "ACAIA", "QUEIMADA", "0.0", "false")

        repository
            .collection(collection)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    result = mapper.fromMap(obj.data)
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }

    suspend fun deletar(id: String): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("debug", "Produto inserido com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }

    suspend fun alterar(produto: Produto): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(produto.id)
            .update(mapper.toMap(produto))
            .addOnSuccessListener {
                Log.d("debug", "Produto inserido com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }
}
