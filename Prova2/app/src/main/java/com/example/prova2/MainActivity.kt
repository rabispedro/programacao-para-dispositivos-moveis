package com.example.prova2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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

        val mainView = MainView(lojaController)

        val views = listOf(mainView, lojaView)

        setContent {
            Prova2Theme {
                PageRouting(views)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PageRouting(views: List<View>) {
    var selectedView by remember { mutableIntStateOf(0) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navigator = rememberNavController()

    NavHost(navigator, startDestination = "Main") {
        views.forEach { view ->
            composable(view.displayName) {
                view.View(navigator, scope, snackbar)
            }
        }
    }

    navigator.graph.forEach {
        Log.d("debug", "Node: $it")
    }

    navigator.

    Scaffold(
        floatingActionButton = { views[selectedView].ActionButton(scope, snackbar) },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackbar) },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        content = { views[selectedView].View(navigator, scope, snackbar) }
    )
}
