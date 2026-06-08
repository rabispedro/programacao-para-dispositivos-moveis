package com.example.a0xc0ffee.controller

import androidx.navigation.NavHostController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Pedido
import com.example.a0xc0ffee.ui.theme.icon.ReceiptIcon

class PedidoController(val mediaController: NavHostController) {
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