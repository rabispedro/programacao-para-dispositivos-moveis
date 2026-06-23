package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.mapper.Mapper
import kotlinx.coroutines.tasks.await
import kotlin.collections.forEach

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

    suspend fun listar(): List<Cliente> {
        val result: MutableList<Cliente> = mutableListOf()

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

    suspend fun listar(texto: String): List<Cliente> {
        val result: MutableList<Cliente> = mutableListOf()

        repository
            .collection(collection)
            .whereEqualTo("cpf", texto)
            .whereEqualTo("nome", texto)
            .whereEqualTo("telefone", texto)
            .whereEqualTo("endereco", texto)
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

    suspend fun buscar(cpf: String): Cliente {
        var result: Cliente = Cliente("11111111111", "Tese", "1193333333", "endereco", "instagram.com/teste")

        repository
            .collection(collection)
            .whereEqualTo("cpf", cpf)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    result = mapper.fromMap(obj.data)
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }

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