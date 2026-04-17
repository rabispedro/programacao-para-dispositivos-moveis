package com.example.atividade2

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var value : TextView
    lateinit var processButton : Button
    lateinit var result : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        value = findViewById<TextView>(R.id.nb_value)
        processButton = findViewById<Button>(R.id.bt_process)
        result = findViewById<TextView>(R.id.pt_result)

        value.text = ""

        processButton.setOnClickListener {
            val isOdd : Boolean = (value.text.toString().toInt() % 2) == 1
            result.text = $"Seu numero é ${if (isOdd) "impar" else "par"}"
            value.text = ""
        }
    }
}