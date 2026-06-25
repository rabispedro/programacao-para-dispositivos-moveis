package com.example.prova2.view

import com.example.prova2.model.Loja

object DataSource {
    private var innerLoja: Loja? = null

    fun getLoja(): Loja? {
        return innerLoja
    }

    fun setLoja(loja: Loja) {
        innerLoja = loja
    }
}