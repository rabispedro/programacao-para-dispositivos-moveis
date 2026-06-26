package com.example.a0xc0ffee.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Pedido
import com.example.a0xc0ffee.model.Relatorio
import com.example.a0xc0ffee.model.mapper.Mapper
import com.example.a0xc0ffee.model.mapper.PedidoMapper
import com.example.a0xc0ffee.model.type.TipoDoGrao
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class RelatorioController(override val mapper: Mapper<Relatorio>) : BaseController<Relatorio>("Relatorio") {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun listarClientesPorTipoDoGrao(callback: (Map<String, List<Cliente>>) -> Unit) {
        val result = HashMap<String, MutableList<Cliente>>()

        Log.d("debug", "Inicializando mapa de Clientes por Tipo de Grão")
        TipoDoGrao.entries.forEach {
            result[it.localize()] = mutableListOf()
        }

        repository
            .collection("Pedido")
            .get()
            .addOnSuccessListener {
                it.forEach { obj ->
                    val entity = PedidoMapper.fromMap(obj.data)

                    entity.items.forEach { item ->
                        if (!result[item.first.tipoDoGrao.localize()]!!.contains(entity.cliente)) {
                            result[item.first.tipoDoGrao.localize()]!!.add(entity.cliente)
                        }
                    }
                }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
                callback(mutableMapOf())
            }
            .await()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarClienteMaiorValor(callback: (List<Cliente>) -> Unit) {
        repository
            .collection("Pedido")
            .get()
            .addOnSuccessListener {
                val result =  it
                    .map { entity -> PedidoMapper.fromMap(entity.data) }
                    .maxBy { entity -> entity.getTotal() }

                callback(listOf(result.cliente))
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
                callback(listOf())
            }
            .await()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarClienteMaiorQuantidade(callback: (List<Cliente>) -> Unit) {
        repository
            .collection("Pedido")
            .get()
            .addOnSuccessListener {
                val result =  it
                    .map { entity -> PedidoMapper.fromMap(entity.data) }
                    .maxBy { entity -> entity.getQuantidadeTotal() }

                callback(listOf(result.cliente))
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarVendasEntreDatas(inicio: LocalDate, fim: LocalDate, callback: (List<Pedido>) -> Unit) {
        repository
            .collection("Pedido")
            .startAfter("data", inicio)
            .endBefore("data", fim)
            .get()
            .addOnSuccessListener {
                val result = it.map { entity -> PedidoMapper.fromMap(entity.data) }
                callback(result)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
                callback(listOf())
            }
            .await()
    }
}