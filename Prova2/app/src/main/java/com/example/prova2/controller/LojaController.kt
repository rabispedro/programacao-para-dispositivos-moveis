package com.example.prova2.controller

import android.util.Log
import com.example.prova2.model.Loja
import com.example.prova2.model.mapper.LojaMapper
import kotlinx.coroutines.tasks.await

class LojaController : Controller("Loja") {
    suspend fun cadastrar(loja: Loja): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(loja.id)
            .set(LojaMapper.toMap(loja))
            .addOnSuccessListener {
                Log.d("debug", "Loja cadastrada com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao cadastrar Loja: $it")
                result = false
            }
            .await()

        return result
    }

    suspend fun listar(): List<Loja> {
        val result: MutableList<Loja> = mutableListOf()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener {
                it.forEach { entity ->
                    Log.d("debug", "Loja listada: ${entity.data}")
                    result.add(LojaMapper.fromMap(entity.data))
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Loja: $it")
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
                Log.d("debug", "Loja deletada com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao deletar Loja: $it")
            }
            .await()

        return result
    }

    suspend fun alterar(loja: Loja): Boolean {
        var result = false

        repository
            .collection(collection)
            .document(loja.id)
            .update(LojaMapper.toMap(loja))
            .addOnSuccessListener {
                Log.d("debug", "Loja alterada com sucesso")
                result = true
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao alterar Loja: $it")
            }
            .await()

        return result
    }
}