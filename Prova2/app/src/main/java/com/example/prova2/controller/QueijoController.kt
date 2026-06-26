package com.example.prova2.controller

import android.util.Log
import com.example.prova2.model.Loja
import com.example.prova2.model.Queijo
import com.example.prova2.model.mapper.QueijoMapper
import com.google.firebase.firestore.AggregateSource
import kotlinx.coroutines.tasks.await

class QueijoController : Controller("Queijo") {
    suspend fun cadastrar(queijo: Queijo, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(queijo.id)
            .set(QueijoMapper.toMap(queijo))
            .addOnSuccessListener {
                val result = Pair(true, "Queijo cadastrado com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao cadastrar queijo")
                callback(result)
            }
            .await()
    }

    suspend fun listar(loja: Loja, callback: (List<Queijo>) -> Unit) {

        repository
            .collection(collection)
            .orderBy("sabor")
            .get()
            .addOnSuccessListener { row ->
                val result: MutableList<Queijo> = mutableListOf()
                if (!row.isEmpty) {
                    row
                        .map { QueijoMapper.fromMap(it.data) }
                        .filter { it.loja.id == loja.id }
                        .forEach {
                            Log.d("debug", "Queijo listado: $it")
                            result.add(it)
                        }
                }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo: $it")
                callback(listOf())
            }
            .await()
    }

    suspend fun listarQueijoMaiosCaro(callback: (List<Queijo>) -> Unit) {
        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                val result: MutableList<Queijo> = mutableListOf()
                if (!row.isEmpty) {
                    val obj = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .maxBy { it.preco.value }

                    result.add(obj)
                }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo: $it")
                callback(listOf())
            }
            .await()
    }

    suspend fun listarQueijoMaiorAroma(callback: (List<Queijo>) -> Unit) {
        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                val result: MutableList<Queijo> = mutableListOf()
                if (!row.isEmpty) {
                    val obj = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .maxBy { it.aroma.nota() }

                    Log.d("debug", "MaiorAroma: $obj")
                    result.add(obj)
                }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo: $it")
                callback(listOf())
            }
            .await()
    }

    suspend fun listarQueijosAparenciaRuim(callback: (List<Loja>) -> Unit) {
        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                val result: MutableList<Loja> = mutableListOf()
                if (!row.isEmpty) {
                    val objs = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .filter { it.aparenciaAgradavel.nota() <= 2 }

                    if (!objs.isEmpty()) {
                        val obj = objs
                            .groupBy { it.loja }
                            .maxBy { it.value.size }
                            .key

                        result.add(obj)
                    }
                }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo de aparência ruim: $it")
                callback(listOf())
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
                Log.d("debug", "Falha ao contar Queijo: $it")
                callback(0)
            }
            .await()
    }

    suspend fun listarValorMedioQueijos(loja: Loja, callback: (Double) -> Unit) {
        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                var result = 0.0
                if (!row.isEmpty) {
                    val objs = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .filter { it.loja.id == loja.id }

                    if (!objs.isEmpty()) {
                        Log.d("debug", "Quantidade de queijos: ${objs.size}")
                        val obj = objs.sumOf { it.preco.value / objs.size }
                        result = obj
                    }
                }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar valor médio de Queijo: $it")
//                callback(0.0)
            }
            .await()
    }

    suspend fun deletar(id: String, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(id)
            .delete()
            .addOnSuccessListener {
                val result = Pair(true, "Queijo deletado com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao deletar queijo")
                callback(result)
            }
            .await()
    }

    suspend fun deletar(loja: Loja, callback: (Pair<Boolean, String>) -> Unit)  {
        val entities = mutableListOf<Queijo>()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                if (!row.isEmpty) {
                    val objs = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .filter { it.loja.id == loja.id }

                    if (!objs.isEmpty()) {
                        Log.d("debug", "Queijo a ser deletado: ${objs.size}")
                        entities.addAll(objs)
                    }
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao deletar Queijo: $it")
            }
            .await()

        entities.forEach {
            repository
                .collection(collection)
                .document(it.id)
                .delete()
                .addOnSuccessListener {
                    val result = Pair(true, "Queijo deletado com sucesso")
                    callback(result)
                }
                .addOnFailureListener {
                    val result = Pair(true, "Falha ao deletar queijo")
                    callback(result)
                }
                .await()
        }
    }

    suspend fun alterar(queijo: Queijo, callback: (Pair<Boolean, String>) -> Unit) {
        repository
            .collection(collection)
            .document(queijo.id)
            .update(QueijoMapper.toMap(queijo))
            .addOnSuccessListener {
                val result = Pair(true, "Queijo alterado com sucesso")
                callback(result)
            }
            .addOnFailureListener {
                val result = Pair(false, "Falha ao alterar queijo")
                callback(result)
            }
            .await()
    }

    suspend fun alterar(loja: Loja, callback: (Pair<Boolean, String>) -> Unit) {
        val entities = mutableListOf<Queijo>()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                if (!row.isEmpty) {
                    val objs = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .filter { it.loja.id == loja.id }
                        .map {
                            Queijo(
                                it.id,
                                it.nome,
                                it.tipo,
                                it.aroma,
                                it.aparenciaAgradavel,
                                it.formatoRegular,
                                it.sabor,
                                it.preco,
                                loja
                            )
                        }

                    if (!objs.isEmpty()) {
                        Log.d("debug", "Queijo a ser alterado: ${objs.size}")
                        entities.addAll(objs)
                    }
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao alterar Queijo: $it")
            }
            .await()

        entities.forEach {
            repository
                .collection(collection)
                .document(it.id)
                .update(QueijoMapper.toMap(it))
                .addOnSuccessListener {
                    val result = Pair(true, "Queijo alterado com sucesso")
                    callback(result)
                }
                .addOnFailureListener {
                    val result = Pair(false, "Falha ao alterar queijo")
                    callback(result)
                }
                .await()
        }
    }
}