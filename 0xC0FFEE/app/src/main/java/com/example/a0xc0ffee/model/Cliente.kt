package com.example.a0xc0ffee.model

import com.example.a0xc0ffee.model.vo.CPF
import com.example.a0xc0ffee.model.vo.Endereco
import com.example.a0xc0ffee.model.vo.Instagram
import com.example.a0xc0ffee.model.vo.Nome
import com.example.a0xc0ffee.model.vo.Telefone

data class Cliente(
    val cpf: CPF,
    val nome: Nome,
    val telefone: Telefone,
    val endereco: Endereco,
    val instagram: Instagram
) {
    constructor(cpf: String, nome: String, telefone: String, endereco: String, instagram: String) :
        this(CPF(cpf), Nome(nome), Telefone(telefone), Endereco(endereco), Instagram(instagram))
}
