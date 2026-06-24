package com.example.a0xc0ffee.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Pedido(
    val id: String,
    val cliente: Cliente,
    val items: Set<Pair<Produto, Int>>,
    val data: LocalDate
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(id: String, cliente: Cliente, items: Set<Pair<Produto, Int>>, data: String) :
            this(id, cliente, items, LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")))

    fun getTotal(): Double {
        var total = 0.0

        items.forEach {
            total += (it.first.valor * it.second)
        }

        return total
    }

    fun getQuantidadeTotal(): Long {
        var total = 0L

        items.forEach {
            total += it.second
        }

        return total
    }
}