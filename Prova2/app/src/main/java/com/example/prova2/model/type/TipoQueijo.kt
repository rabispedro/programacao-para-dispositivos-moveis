package com.example.prova2.model.type

import java.util.Locale

enum class TipoQueijo {
    CURADO,
    MEIA_CURA,
    FRESCO;

    fun capitalize(): String {
        return this.name
            .lowercase()
            .replace("_"," ")
            .capitalize(Locale.ROOT);
    }
}