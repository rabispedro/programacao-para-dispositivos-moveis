package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var firstValue : EditText
    lateinit var secondValue : EditText
    lateinit var magicButton : Button
    lateinit var result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        firstValue = findViewById<EditText>(R.id.firstValueEditText)
        secondValue = findViewById<EditText>(R.id.secondValueEditText)
        magicButton = findViewById<Button>(R.id.magicButton)
        result = findViewById<TextView>(R.id.resultTextView)

        magicButton.setOnClickListener {
            val firstValueParsed = firstValue.text.toString().toDouble()
            val secondValueParsed = secondValue.text.toString().toDouble()

            Log.v(this.packageName, firstValue.text.toString())

            result.text = "Resultado: ${firstValueParsed + secondValueParsed}"

            firstValue.text.clear()
            secondValue.text.clear()
        }

    }
}
