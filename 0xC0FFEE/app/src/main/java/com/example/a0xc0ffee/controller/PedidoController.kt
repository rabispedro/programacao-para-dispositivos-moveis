package com.example.a0xc0ffee.controller

import androidx.navigation.NavHostController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Pedido

class PedidoController: BaseController("Pedido") {
    fun cadastrar(cpfCliente: String): Boolean {
        return false
    }

    fun listar(cliente: Cliente): List<Pedido> {
        return listOf()
    }

    fun deletar(id: String): Boolean {
        return false
    }

    fun alterar(pedido: Pedido): Boolean {
        return false
    }
}