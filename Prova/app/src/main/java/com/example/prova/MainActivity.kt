package com.example.prova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.prova.client.ClientController
import com.example.prova.client.ClientService
import com.example.prova.computer.ComputerController
import com.example.prova.computer.ComputerService
import com.example.prova.shared.Database
import com.example.prova.shared.SimpleFormView
import com.example.prova.ui.theme.BackgroundColor
import com.example.prova.ui.theme.PrimaryColor
import com.example.prova.ui.theme.SegmentedButtonThemedColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = Room
            .databaseBuilder(
                applicationContext,
                Database::class.java,
                "database"
            )
            .build()

        val computerRepository = database.computerRepository()
        val computerService = ComputerService(computerRepository)
        val computerController = ComputerController(computerService)

        val clientRepository = database.clientRepository()
        val clientService = ClientService(clientRepository)
        val clientController = ClientController(clientService)

        val controllers: Set<SimpleFormView> = setOf(clientController, computerController)

        setContent {
            View(controllers)
        }
    }

    @Composable
    private fun header(controllers: Set<SimpleFormView>): Int {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = controllers.indices

        SingleChoiceSegmentedButtonRow (modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryColor)
                .padding(top = 38.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {
            options.forEach {
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = it, count = controllers.size),
                    colors = SegmentedButtonThemedColors,
                    onClick = { selectedIndex = it },
                    selected = it == selectedIndex,
                    label = { Text(getStepName(controllers, it)) },
                    icon = {}
                )
            }
        }

        return selectedIndex
    }

    @Composable
    fun View(controllers: Set<SimpleFormView>) {
        var index by remember { mutableIntStateOf(1) }

        Column(modifier = Modifier
            .background(BackgroundColor)
            .fillMaxWidth()
            .fillMaxHeight()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
            ) {
                index = header(controllers)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .align(alignment = Alignment.CenterHorizontally)
            ) {
                controllers.elementAt(index).SimpleView()
            }
        }
    }

    private fun getStepName(controllers: Set<SimpleFormView>, index: Int): String {
        if (controllers.indices.contains(index))
            return controllers.elementAt(index).javaClass.simpleName.replace("Controller", "")
        return ""
    }
}
