package com.example.prova2.controller

import android.util.Log
import com.example.prova2.model.Queijo
import com.example.prova2.model.mapper.QueijoMapper
import kotlinx.coroutines.tasks.await

class QueijoController : Controller("Queijo") {
    suspend fun cadastrar(queijo: Queijo): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(queijo.id)
            .set(QueijoMapper.toMap(queijo))
            .addOnSuccessListener {
                Log.d("debug", "Queijo cadastrado com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao cadastrar Queijo: $it")
                result = false
            }
            .await()

        return result
    }

    suspend fun listar(): List<Queijo> {
        val result: MutableList<Queijo> = mutableListOf()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener {
                it.forEach { entity ->
                    Log.d("debug", "Queijo listado: ${entity.data}")
                    result.add(QueijoMapper.fromMap(entity.data))
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo: $it")
                result.clear()
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
                Log.d("debug", "Queijo deletado com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao deletar Queijo: $it")
            }
            .await()

        return result
    }

    suspend fun alterar(queijo: Queijo): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(queijo.id)
            .update(QueijoMapper.toMap(queijo))
            .addOnSuccessListener {
                Log.d("debug", "Queijo alterado com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao alterar Queijo: $it")
            }
            .await()

        return result
    }
}