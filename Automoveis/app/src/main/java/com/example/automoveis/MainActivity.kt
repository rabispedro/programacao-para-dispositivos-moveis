package com.example.automoveis

import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.automoveis.model.Automovel
import com.example.automoveis.model.Caminhao
import com.example.automoveis.model.Carro
import com.example.automoveis.model.Onibus
import com.example.automoveis.ui.theme.AccentColor
import com.example.automoveis.ui.theme.BackgroundColor
import com.example.automoveis.ui.theme.ErrorColor
import com.example.automoveis.ui.theme.PrimaryColor
import com.example.automoveis.ui.theme.SecondaryColor
import com.example.automoveis.ui.theme.SegmentedButtonThemedColors
import com.example.automoveis.ui.theme.TextColor
import com.example.automoveis.view.CaminhaoView
import com.example.automoveis.view.CarroView
import com.example.automoveis.view.OnibusView
import com.example.automoveis.view.StepView
import com.example.automoveis.viewmodel.CaminhaoViewModel
import com.example.automoveis.viewmodel.CarroViewModel
import com.example.automoveis.viewmodel.OnibusViewModel

class MainActivity : ComponentActivity(), IObserver {
    private lateinit var viewsFactory : Map<Int, StepView>
    private lateinit var vehicles : SnapshotStateList<Automovel>

    val caminhaoViewModel = CaminhaoViewModel(listOf<IObserver>(this) as MutableList<IObserver>)
    val carroViewModel = CarroViewModel(listOf<IObserver>(this) as MutableList<IObserver>)
    val onibusViewModel = OnibusViewModel(listOf<IObserver>(this) as MutableList<IObserver>)

    val caminhaoView = CaminhaoView(caminhaoViewModel)
    val carroView = CarroView(carroViewModel)
    val onibusView = OnibusView(onibusViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            viewsFactory = remember {
                mutableMapOf(
                    Pair<Int, StepView>(1, caminhaoView),
                    Pair<Int, StepView>(2, carroView),
                    Pair<Int, StepView>(3, onibusView)
                )
            }

            vehicles = remember { mutableStateListOf<Automovel>() }

            View()
        }
    }

    fun getStepName(step: Int): String {
        return when(step) {
            1 -> "Caminhão"
            2 -> "Carro"
            3 -> "Ônibus"
            else -> ""
        }
    }

    @Composable
    fun header(): Int {
        var selectedIndex by remember { mutableIntStateOf(1) }
        val options = viewsFactory.keys

        SingleChoiceSegmentedButtonRow(modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryColor)
            .padding(top = 38.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                options.forEach {
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = it-1,
                            count = 3,
                        ),
                        colors = SegmentedButtonThemedColors,
                        onClick = { selectedIndex = it },
                        selected = it == selectedIndex,
                        label = { Text(getStepName(it)) },
                        icon = {}
                    )
                }
        }

        return selectedIndex
    }

    @Composable
    fun Footer() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(PrimaryColor)) {
            vehicles.toList().forEach { vehicle ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(SecondaryColor)
                    .border(color = BackgroundColor, width = 1.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(vehicle.toString(), color = TextColor, fontWeight = FontWeight.Bold)
                    IconButton(
                        onClick = {
                            when(vehicle.javaClass.name) {
                                 "com.example.automoveis.model.Caminhao" ->
                                     caminhaoViewModel.remove(vehicle as Caminhao)
                                 "com.example.automoveis.model.Carro" ->
                                     carroViewModel.remove(vehicle as Carro)
                                 "com.example.automoveis.model.Onibus" ->
                                     onibusViewModel.remove(vehicle as Onibus)
                             }
                        },
                        content = {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Remover", tint = ErrorColor)
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun View() {
        var index by remember { mutableIntStateOf(1) }

        Column(modifier = Modifier
            .background(BackgroundColor)
            .fillMaxWidth()
            .fillMaxHeight()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
            ) {
                index = header()
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .align(alignment = Alignment.CenterHorizontally)
            ) {
                viewsFactory[index]!!.Show()
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
            ) {
                Footer()
            }
        }
    }

    override fun update() {
        val carros = carroViewModel.listaTodos()
        val caminhoes = caminhaoViewModel.listaTodos()
        val onibus = onibusViewModel.listaTodos()

        vehicles.clear()

        vehicles.addAll(carros)
        vehicles.addAll(caminhoes)
        vehicles.addAll(onibus)

        Log.d("debug", "Veículos: ${vehicles.size}")

        for (vehicle in vehicles) {
            Log.d("debug", vehicle.toString())
        }
    }
}
