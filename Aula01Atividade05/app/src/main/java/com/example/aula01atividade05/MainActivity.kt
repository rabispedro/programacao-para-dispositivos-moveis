package com.example.aula01atividade05

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    val primes: MutableSet<Long> = setOf(2L, 3L, 5L, 7L, 11L) as MutableSet<Long>
    lateinit var number : TextView
    lateinit var processButton : Button
    lateinit var result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        number = findViewById(R.id.tv_number)
        processButton = findViewById(R.id.bt_process)
        result = findViewById(R.id.tv_result)

        processButton.setOnClickListener {
            val n = number.text.toString().toLong()
            result.text = "$n ${if (isPrime(n)) "" else "não"} é primo"

            Log.i("debug", "Primos: $primes")
        }
    }

    fun isPrime(number: Long): Boolean {
        if(number <= 1L) {
            return false
        }

        if (primes.contains(number)) {
            return true
        }

        primes.forEach {
            if (number % it == 0L) {
                return false
            }
        }

        val upperBound = sqrt(number.toDouble()).toLong()

        for (i in primes.last() .. upperBound step 2) {
            if (number % i == 0L) {
                return false
            }
        }

        primes.add(number)
        return true
    }
}