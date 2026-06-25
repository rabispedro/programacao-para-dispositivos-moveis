package com.example.prova2.controller

import android.util.Log
import com.example.prova2.model.Loja
import com.example.prova2.model.Queijo
import com.example.prova2.model.mapper.QueijoMapper
import com.google.firebase.firestore.AggregateSource
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

    suspend fun listar(loja: Loja): List<Queijo> {
        val result: MutableList<Queijo> = mutableListOf()

        Log.d("debug", "Listar por loja: ${loja.nome}")

        repository
            .collection(collection)
            .orderBy("sabor")
            .get()
            .addOnSuccessListener { row ->
                if (!row.isEmpty) {
                    row
                        .map { QueijoMapper.fromMap(it.data) }
                        .filter { it.loja.id == loja.id }
                        .forEach {
                            Log.d("debug", "Queijo listado: $it")
                            result.add(it)
                        }
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo: $it")
                result.clear()
            }
            .await()

        return result
    }

    suspend fun listarQueijoMaiosCaro(): List<Queijo> {
        val result: MutableList<Queijo> = mutableListOf()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                if (!row.isEmpty) {
                    val obj = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .maxBy { it.preco.value }

                    result.add(obj)
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo: $it")
                result.clear()
            }
            .await()

        return result
    }

    suspend fun listarQueijoMaiorAroma(): List<Queijo> {
        val result: MutableList<Queijo> = mutableListOf()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                if (!row.isEmpty) {
                    val obj = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .maxBy { it.aroma.nota() }

                    Log.d("debug", "MaiorAroma: $obj")
                    result.add(obj)
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo: $it")
                result.clear()
            }
            .await()

        return result
    }

    suspend fun listarQueijosAparenciaRuim(): List<Loja> {
        val result: MutableList<Loja> = mutableListOf()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
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
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar Queijo de aparência ruim: $it")
                result.clear()
            }
            .await()

        return result
    }

    suspend fun contar(): Int {
        var result = 0

        repository
            .collection(collection)
            .count()
            .get(AggregateSource.SERVER)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result = task.result.count.toInt()
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao contar Queijo: $it")
            }
            .await()

        return result
    }

    suspend fun listarValorMedioQueijos(loja: Loja): Double {
        var result = 0.0

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                if (!row.isEmpty) {
                    val objs = row
                        .map { QueijoMapper.fromMap(it.data) }
                        .filter { it.loja.id == loja.id }

                    if (!objs.isEmpty()) {
                        Log.d("debug", "Quantidade de queijos: ${objs.size}")

                        val obj = objs
                            .sumOf { it.preco.value / objs.size }

                        result = obj
                    }
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha ao listar valor médio de Queijo: $it")
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

    suspend fun deletar(loja: Loja): Boolean {
        var result = false
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
                        result = true
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
                    Log.d("debug", "Queijo deletado com sucesso")
                }
                .addOnFailureListener {
                    Log.d("debug", "Falha ao deletar Queijo: $it")
                }
                .await()
        }

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

    suspend fun alterar(loja: Loja): Boolean {
        var result = false
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
                        result = true
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
                    Log.d("debug", "Queijo alterado com sucesso")
                    result = true
                }
                .addOnFailureListener {
                    Log.d("debug", "Falha ao alterar Queijo: $it")
                }
                .await()
        }

        return result
    }


}