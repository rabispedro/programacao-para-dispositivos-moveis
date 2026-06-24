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
    suspend fun listarClientesPorTipoDoGrao(): Map<String, List<Cliente>> {
        val result = HashMap<String, MutableList<Cliente>>()

        Log.d("debug", "Inicializando mapa de Clientes por Tipo de Grão")
        TipoDoGrao.entries.forEach {
            result[it.localize()] = mutableListOf()
        }

        repository
            .collection("Pedido")
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    val entity = PedidoMapper.fromMap(obj.data)

                    entity.items.forEach {
                        if (!result[it.first.tipoDoGrao.localize()]!!.contains(entity.cliente)) {
                            result[it.first.tipoDoGrao.localize()]!!.add(entity.cliente)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        Log.d("debug", "Finalizando mapa de Clientes por Tipo de Grão")
        result.keys.forEach {
            Log.d("debug", "Grão ${it}: ${result[it]!!.size}")
        }

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarClienteMaiorValor(): List<Cliente> {
        val result: MutableList<Cliente> = mutableListOf()

        repository
            .collection("Pedido")
            .get()
            .addOnSuccessListener { row ->
                row.forEach {
                    val obj = PedidoMapper.fromMap(it.data)
                    Log.d("debug", "Item com total: ${obj.getTotal()}")
                }

                val obj =  row
                    .map { PedidoMapper.fromMap(it.data) }
                    .maxBy { it.getTotal() }

                Log.d("debug", "Pedido com maior valor: $obj")
                result.add(obj.cliente)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarClienteMaiorQuantidade(): List<Cliente> {
        val result: MutableList<Cliente> = mutableListOf()

        repository
            .collection("Pedido")
            .get()
            .addOnSuccessListener { row ->
                row.forEach {
                    val obj = PedidoMapper.fromMap(it.data)
                    Log.d("debug", "Item com quantidade total: ${obj.getQuantidadeTotal()}")
                }

                val obj =  row
                    .map { PedidoMapper.fromMap(it.data) }
                    .maxBy { it.getQuantidadeTotal() }

                Log.d("debug", "Pedido com maior quantidade: $obj")
                result.add(obj.cliente)
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarVendasEntreDatas(inicio: LocalDate, fim: LocalDate): List<Pedido> {
        val result: MutableList<Pedido> = mutableListOf()

        repository
            .collection("Pedido")
            .startAfter("data", inicio)
            .endBefore("data", fim)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    val entity = PedidoMapper.fromMap(obj.data)
                    result.add(entity)
                }
            }
            .addOnFailureListener {
                Log.d("debug", "Falha: $it")
            }
            .await()

        return result
    }
}