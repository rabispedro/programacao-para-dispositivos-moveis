package com.example.a0xc0ffee.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.a0xc0ffee.model.vo.CPF
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Pedido(
    val id: String,
    val data: LocalDate,
    val cpfCliente: CPF
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(id: String, data: String, cpfCliente: String) :
            this(id, LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")), CPF(cpfCliente))
}