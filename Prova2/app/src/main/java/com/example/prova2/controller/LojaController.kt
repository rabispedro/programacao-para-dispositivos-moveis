package com.example.prova2.controller

import android.util.Log
import com.example.prova2.model.Loja
import com.example.prova2.model.mapper.LojaMapper
import com.google.firebase.firestore.AggregateSource
import kotlinx.coroutines.tasks.await

class LojaController : Controller("Loja") {
    suspend fun cadastrar(loja: Loja, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(loja.id)
            .set(LojaMapper.toMap(loja))
            .addOnSuccessListener {
                callback(Pair(true, "Loja cadastrada com sucesso"))
            }
            .addOnFailureListener {
                callback(Pair(false, "Falha ao cadastrar loja"))
            }
            .await()
    }

    suspend fun listar(calback: (List<Loja>) -> Unit) {
        repository
            .collection(collection)
            .get()
            .addOnSuccessListener {
                val result = it
                    .map { entity -> LojaMapper.fromMap(entity.data) }
                calback(result)
            }
            .addOnFailureListener {
                Log.d("debug2", "Falha ao listar Loja: $it")
            }
            .await()
    }

    suspend fun contar(callback: (Int) -> Unit) {
        repository
            .collection(collection)
            .count()
            .get(AggregateSource.SERVER)
            .addOnSuccessListener {
                val result = it.count.toInt()
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao contar Loja: $it")
                callback(0)
            }
            .await()
    }

    suspend fun deletar(id: String, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(id)
            .delete()
            .addOnSuccessListener {
                val result = Pair(true, "Loja deletada com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao deletar loja")
                callback(result)
            }
            .await()
    }

    suspend fun alterar(loja: Loja, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(loja.id)
            .update(LojaMapper.toMap(loja))
            .addOnSuccessListener {
                val result = Pair(true, "Loja alterada com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(true, "Falha ao alterar loja")
                callback(result)
            }
            .await()
    }
}