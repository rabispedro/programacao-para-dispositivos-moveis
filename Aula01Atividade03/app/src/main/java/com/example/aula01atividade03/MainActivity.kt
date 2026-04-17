package com.example.aula01atividade03

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    lateinit var aValue : TextView
    lateinit var bValue : TextView
    lateinit var cValue : TextView
    lateinit var buttonProcess : Button
    lateinit var rootsResult : TextView
    lateinit var concavityResult : TextView
    lateinit var vertexResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        aValue = findViewById(R.id.tv_a)
        bValue = findViewById(R.id.tv_b)
        cValue = findViewById(R.id.tv_c)
        buttonProcess = findViewById(R.id.bt_process)

        rootsResult = findViewById(R.id.tv_root)
        concavityResult = findViewById(R.id.tv_concavity)
        vertexResult = findViewById(R.id.tv_vertex)

        resetInputs()
        buttonProcess.setOnClickListener {
            val a = aValue.text.toString().toDouble()
            val b = bValue.text.toString().toDouble()
            val c = cValue.text.toString().toDouble()
            val roots = bhaskara(a, b, c)
            val vertex = calculateVertex(a, b, c)

            resetInputs()

            rootsResult.text = "Raízes: ${roots.first.toDouble()} e ${roots.second.toDouble()}"
            concavityResult.text = "Concavidade: para ${if(a > 0.0) "cima" else "baixo"}"
            vertexResult.text = "Vértice: (${vertex.first.toDouble()}, ${vertex.second.toDouble()})"
        }
    }

    fun calculateVertex(a: Double, b: Double, c: Double): Pair<Double, Double> {
        if (a.equals(0.0)) {
            return Pair(0.0, 0.0)
        }

        val delta = sqrt((b * b) - (4.0 * a * c))

        val xv = (-b) / (2.0 * a)
        val yv = (-delta) / (4.0 * a)

        return Pair(xv, yv)
    }

    fun bhaskara(a: Double, b: Double, c: Double): Pair<Number, Number> {
        if (a.equals(0.0)) {
            return Pair(0.0, 0.0)
        }

        val delta = sqrt((b * b) - (4.0 * a * c))

        if (delta < 0.0) {
            return Pair(0.0, 0.0)
        }

        val firstRoot = (-b - delta) / (2.0 * a)
        val secondRoot = (-b + delta) / (2.0 * a)

        return Pair(firstRoot, secondRoot)
    }

    fun resetInputs() {
        aValue.text = ""
        bValue.text = ""
        cValue.text = ""
    }
}