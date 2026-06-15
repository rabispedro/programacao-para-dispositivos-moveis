package com.example.a0xc0ffee.controller

import androidx.navigation.NavHostController
import com.example.a0xc0ffee.model.Produto
import com.example.a0xc0ffee.model.type.PontoDaTorra
import com.example.a0xc0ffee.model.type.TipoDoGrao

class ProdutoController: BaseController("Produto") {
    fun cadastrar(produto: Produto): Boolean {
        return false
    }

    fun listar(): List<Produto> {
        return listOf()
    }

    fun buscar(id: String): Produto {
        return Produto("", TipoDoGrao.CONILON, PontoDaTorra.QUEIMADA, 0.0, false)
    }

    fun deletar(id: String): Boolean {
        return false
    }

    fun alterar(produto: Produto): Boolean {
        return false
    }
}