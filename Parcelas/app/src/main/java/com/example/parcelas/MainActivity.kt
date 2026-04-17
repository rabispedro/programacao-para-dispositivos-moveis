package com.example.parcelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.parcelas.view.CaminhaoView
import com.example.parcelas.view.CarroView
import com.example.parcelas.view.OnibusView
import com.example.parcelas.view.StepView
import com.example.parcelas.viewmodel.CaminhaoViewModel
import com.example.parcelas.viewmodel.CarroViewModel
import com.example.parcelas.viewmodel.OnibusViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewsFactory : Map<Int, StepView>
    private lateinit var currentView : StepView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val caminhaoViewModel = CaminhaoViewModel()
        val carroViewModel = CarroViewModel()
        val onibusViewModel = OnibusViewModel()

        val caminhaoView = CaminhaoView(caminhaoViewModel)
        val carroView = CarroView(carroViewModel)
        val onibusView = OnibusView(onibusViewModel)

        viewsFactory = mapOf(
            Pair<Int, StepView>(1, caminhaoView),
            Pair<Int, StepView>(2, carroView),
            Pair<Int, StepView>(3, onibusView)
        )

        currentView = viewsFactory[1]!!

        setContent
    }

    fun changeStep(step: Int) {
        when(step) {
            1, 2, 3 -> currentView = viewsFactory[step]!!
        }
    }

    @Composable
    fun header() {

    }

    @Composable
    fun footer() {

    }

    @Composable
    fun view() {
        header()
        currentView.show()
        footer()
    }
}