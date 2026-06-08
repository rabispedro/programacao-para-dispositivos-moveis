package com.example.a0xc0ffee.controller

import androidx.navigation.NavHostController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.vo.CPF
import com.example.a0xc0ffee.model.vo.Endereco
import com.example.a0xc0ffee.model.vo.Instagram
import com.example.a0xc0ffee.model.vo.Nome
import com.example.a0xc0ffee.model.vo.Telefone
import com.example.a0xc0ffee.ui.theme.icon.StoreFrontIcon

class ClienteController(val controller: NavHostController) {
    fun cadastrar(): Boolean {
        return false
    }

    fun listar(): List<Cliente> {
        return listOf()
    }

    fun buscar(cpf: String): Cliente {
        return Cliente(CPF(""), Nome(""), Telefone(""), Endereco(""), Instagram(""))
    }

    fun deletar(cpf: String): Boolean {
        return false
    }

    fun alterar(cliente: Cliente): Boolean {
        return false
    }
}