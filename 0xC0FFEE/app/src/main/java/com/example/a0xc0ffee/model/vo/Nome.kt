package com.example.a0xc0ffee.model.vo

data class Nome(val value: String): ValueObject {
    init {
        if (!isValid()){
            throw IllegalArgumentException("Nome is not valid")
        }
    }

    override fun isValid(): Boolean {
        return Regex("""[a-zA-Z\s]{2,100}""").matches(value)
    }
}