package com.example.a0xc0ffee.model.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.a0xc0ffee.model.Pedido
import com.example.a0xc0ffee.model.Produto
import java.time.format.DateTimeFormatter

object PedidoMapper: Mapper<Pedido> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun toMap(model: Pedido): Map<String, Any> {

        return mapOf(
            "id" to model.id,
            "cliente" to ClienteMapper.toMap(model.cliente),
            "items" to model.items.map {
                mapOf(
                    "produto" to ProdutoMapper.toMap(it.first),
                    "quantidade" to it.second.toString()
                )
            }.toList(),
            "data" to model.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun fromMap(map: Map<String, Any>): Pedido {
        val items = HashSet<Pair<Produto, Int>>()

        (map["items"] as List<Map<String, Any>>).forEach {
            val produto = ProdutoMapper.fromMap(it["produto"] as Map<String, Any>)
            val quantidade = (it["quantidade"] as String).toInt()
            items.add(Pair(produto, quantidade))
        }

        return Pedido(
            map["id"] as String,
            ClienteMapper.fromMap(map["cliente"]!! as Map<String, Any>),
            items,
            map["data"] as String
        )
    }
}

