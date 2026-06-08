package com.example.a0xc0ffee.controller

import androidx.navigation.NavHostController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.ItemPedido
import com.example.a0xc0ffee.model.vo.CPF
import com.example.a0xc0ffee.model.vo.Endereco
import com.example.a0xc0ffee.model.vo.Instagram
import com.example.a0xc0ffee.model.vo.Nome
import com.example.a0xc0ffee.model.vo.Telefone
import com.example.a0xc0ffee.ui.theme.icon.ReportIcon
import java.time.LocalDate

class RelatorioController(val resourceController: NavHostController) {
    fun listarClientesPorTipoDoGrao(tipoDoGrao: String): List<Cliente> {
        return listOf()
    }

    fun buscarClienteMaiorValor(): Cliente {
        return Cliente(CPF(""), Nome(""), Telefone(""), Endereco(""), Instagram(""))
    }

    fun buscarClienteMaiorQuantidade(): Cliente {
        return Cliente(CPF(""), Nome(""), Telefone(""), Endereco(""), Instagram(""))
    }

    fun buscarVendasEntreDatas(inicio: LocalDate, fim: LocalDate): List<ItemPedido> {
        return listOf()
    }
}