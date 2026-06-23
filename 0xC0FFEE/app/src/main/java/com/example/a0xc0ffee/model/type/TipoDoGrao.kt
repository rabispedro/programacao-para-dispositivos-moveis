package com.example.a0xc0ffee.model.type

import java.util.Locale

// NOTE: fonte https://www.mokaclube.com.br/blogs/news/tipos-de-graos-de-cafe?srsltid=AfmBOoqQn7I8EM8KXTiq6jyECBhCfTFBIOSMuiUF_8OxnP5ITPFgF5Iz
enum class TipoDoGrao {
    ARABICA,
    CONILON,
    BOURBON,
    ACAIA,
    CATUAI,
    ROBUSTA,
    GEISHA,
    KONA;

    fun localize(): String {
        return this.name.lowercase().replace("_", " ").capitalize(Locale.ROOT)
    }
}