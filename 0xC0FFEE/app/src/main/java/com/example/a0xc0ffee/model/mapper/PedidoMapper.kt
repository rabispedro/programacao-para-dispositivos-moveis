package com.example.a0xc0ffee.model.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.a0xc0ffee.model.Pedido

object PedidoMapper: Mapper<Pedido> {
    override fun toMap(model: Pedido): Map<String, Any> {
        return mapOf(
            "id" to model.id,
            "data" to model.data,
            "cpfCliente" to model.cpfCliente.value
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun fromMap(map: Map<String, Any>): Pedido {
        return Pedido(
            map["id"] as String,
            map["data"] as String,
            map["cpfCliente"] as String,
        )
    }
}
