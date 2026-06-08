package com.example.a0xc0ffee.model.vo

data class Instagram(val value: String): ValueObject {
    init {
        if (!isValid()){
            throw IllegalArgumentException("Instagram is not valid")
        }
    }

    override fun isValid(): Boolean {
        return Regex("""((https://www\.)|(www\.))?instagram\.com/\w{3,50}""").matches(value)
    }
}