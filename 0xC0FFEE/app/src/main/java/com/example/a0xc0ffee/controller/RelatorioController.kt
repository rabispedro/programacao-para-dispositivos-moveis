package com.example.a0xc0ffee.controller

import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.ItemPedido
import com.example.a0xc0ffee.model.vo.CPF
import com.example.a0xc0ffee.model.vo.Endereco
import com.example.a0xc0ffee.model.vo.Instagram
import com.example.a0xc0ffee.model.vo.Nome
import com.example.a0xc0ffee.model.vo.Telefone
import java.time.LocalDate

class RelatorioController: BaseController("Relatorio") {
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