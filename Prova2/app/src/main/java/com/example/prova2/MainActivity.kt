package com.example.prova2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova2.controller.LojaController
import com.example.prova2.controller.QueijoController
import com.example.prova2.ui.theme.Prova2Theme
import com.example.prova2.view.LojaView
import com.example.prova2.view.MainView
import com.example.prova2.view.View

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val queijoController = QueijoController()
        val lojaController = LojaController()

        val lojaView = LojaView(lojaController, queijoController)
        val mainView = MainView(lojaController, queijoController)

        val views = listOf(mainView, lojaView)

        setContent {
            Prova2Theme {
                PageRouting(views)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PageRouting(views: List<View>) {
    var selectedView by remember { mutableIntStateOf(0) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navigator = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { views[selectedView].TopBar(navigator, scope, snackbar) }) },
        floatingActionButton = { views[selectedView].ActionButton(navigator, scope, snackbar) },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackbar) },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        content = { innerPadding ->
            NavHost(navigator, startDestination = "MainView", Modifier.padding(innerPadding)) {
                views.forEach { view ->
                    composable(view.javaClass.simpleName) {
                        Log.d("debug", "Rota composta: ${view.javaClass.simpleName}")
                        selectedView = views.indexOf(view)
                        view.View(navigator, scope, snackbar)
                    }
                }
            }
        }
    )
}
