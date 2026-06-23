package com.example.a0xc0ffee.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.ItemPedido
import com.example.a0xc0ffee.model.Relatorio
import com.example.a0xc0ffee.model.mapper.Mapper
import com.example.a0xc0ffee.model.vo.CPF
import com.example.a0xc0ffee.model.vo.Endereco
import com.example.a0xc0ffee.model.vo.Instagram
import com.example.a0xc0ffee.model.vo.Nome
import com.example.a0xc0ffee.model.vo.Telefone
import java.time.LocalDate

class RelatorioController(override val mapper: Mapper<Relatorio>) : BaseController<Relatorio>("Relatorio") {
    suspend fun listarClientesPorTipoDoGrao(): Map<String, List<Cliente>> {
        return HashMap<String, List<Cliente>>()
    }

    suspend fun buscarClienteMaiorValor(): Cliente? {
        return null
    }

    suspend fun buscarClienteMaiorQuantidade(): Cliente? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarVendasEntreDatas(inicio: LocalDate? = LocalDate.now(), fim: LocalDate?): List<ItemPedido> {
        return listOf()
    }
}