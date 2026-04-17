package com.example.prova.computer

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.prova.client.Client

@Entity("computer")
data class Computer(
    @PrimaryKey
    val id: String,
    val model: String,
    val memory: Double,
    val price: Double,
    val clientCpf: String
) {
    fun isValid(): Boolean {
        return (
            model.isNotBlank() && model.length < 50 &&
            memory.isFinite() && memory > 0.0 &&
            price.isFinite() && price > 0.0
        )
    }

    override fun toString(): String {
        return "$id | $model | $memory | $price | $clientCpf"
    }
}

data class ComputerByClient(
    @Embedded
    val client: Client,
    @Relation(
        parentColumn = "cpf",
        entityColumn = "clientCpf"
    )
    val computer: Computer
) {
    override fun toString(): String {
        return "${client.email} | $computer"
    }
}
