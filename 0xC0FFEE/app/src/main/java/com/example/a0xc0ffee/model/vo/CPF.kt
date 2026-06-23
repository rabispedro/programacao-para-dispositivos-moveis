package com.example.a0xc0ffee.model.vo

data class CPF(val value: String): ValueObject {
    init {
        if (!isValid()){
            throw IllegalArgumentException("CPF is not valid")
        }
    }

    override fun isValid(): Boolean {
        return Regex("""(\d{11})|(\d{3}\.\d{3}\.\d{3}-\d{2})""").matches(value)
    }

    fun safeShow(): String {
        return value.substring(0, 3) + ".XXX.XXX-" + value.substring(9)
    }
}