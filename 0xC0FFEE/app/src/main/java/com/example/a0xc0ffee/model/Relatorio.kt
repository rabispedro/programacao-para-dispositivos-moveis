package com.example.a0xc0ffee.model

import com.example.a0xc0ffee.model.vo.CPF

data class Relatorio(val id: String, val cpf: CPF) {
    constructor(id: String, cpf: String) :
            this(id, CPF(cpf))
}