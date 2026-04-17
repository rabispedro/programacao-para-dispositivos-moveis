package com.example.prova.client

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("client")
data class Client(
    @PrimaryKey
    val cpf: String,
    val name: String,
    val email: String,
    val phone: String
) {
    fun isValid(): Boolean {
        return (
            cpf.isNotBlank() && cpf.matches("""\d{11}""".toRegex()) &&
            name.isNotBlank() && name.length < 50 &&
            email.isNotBlank() && email.matches(""".*@.*""".toRegex()) &&
            phone.isNotBlank() && phone.matches("""\d{11}""".toRegex())
        )
    }

    override fun toString(): String {
        return "$cpf | $name | $email | $phone"
    }
}
