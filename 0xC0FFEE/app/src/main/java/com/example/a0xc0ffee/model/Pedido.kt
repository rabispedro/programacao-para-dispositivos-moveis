package com.example.a0xc0ffee.model

import com.example.a0xc0ffee.model.vo.CPF
import java.time.LocalDate

data class Pedido(
    val id: String,
    val data: LocalDate,
    val cpfCliente: CPF
) {}