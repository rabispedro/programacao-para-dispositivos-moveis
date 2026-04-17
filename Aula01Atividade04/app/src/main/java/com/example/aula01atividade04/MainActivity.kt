package com.example.aula01atividade04

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var pointA : TextView
    lateinit var pointB : TextView
    lateinit var result : TextView
    lateinit var magicButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        pointA = findViewById(R.id.et_point_a)
        pointB = findViewById(R.id.et_point_b)
        result = findViewById(R.id.tv_result)
        magicButton = findViewById(R.id.bt_process)

        magicButton.setOnClickListener {
            val a = extractPoint(pointA.text.toString())
            val b = extractPoint(pointB.text.toString())

            Log.v("debug", "Ponto A: $a")
            Log.v("debug", "Ponto B: $b")

            result.text = "Distância: ${Math.hypot(a.first - b.first,  a.second - b.second)}"

            resetInputs()
        }
    }

    fun extractPoint(input: String): Pair<Double, Double> {
        if (input.matches("""\(-?\d*.?\d*, -?\d*.?\d*\)""".toRegex())) {
            val numbers = input
                .replace("(", "")
                .replace(",", "")
                .replace(")", "")
                .split(" ")

            return Pair(numbers[0].toDouble(), numbers[1].toDouble())
        }

        return Pair(0.0, 0.0)
    }

    fun resetInputs() {
        pointA.text = ""
        pointB.text = ""
    }
}