package com.example.prova2.model.type

import java.util.Locale

enum class FormatoRegularQueijo {
    MUITO_RUIM,
    RUIM,
    MEDIO,
    BOM,
    MUITO_BOM;

    fun capitalize(): String {
        return this.name
            .lowercase()
            .replace("_"," ")
            .capitalize(Locale.ROOT);
    }

    fun nota(): Int {
        return this.ordinal + 1
    }
}