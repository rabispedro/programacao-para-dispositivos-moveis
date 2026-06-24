package com.example.prova2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova2.controller.LojaController
import com.example.prova2.controller.QueijoController
import com.example.prova2.ui.theme.Prova2Theme
import com.example.prova2.view.LojaView
import com.example.prova2.view.QueijoView
import com.example.prova2.view.View

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val queijoController = QueijoController()
        val lojaController = LojaController()

        val queijoView = QueijoView(queijoController)
        val lojaView = LojaView(lojaController)

        val views = listOf(lojaView, queijoView)

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

    NavHost(navigator, startDestination = "Loja") {
        views.forEach { view ->
            composable(view.displayName) {
                view.View(scope, snackbar)
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
                views.forEachIndexed { index, view ->
                    NavigationBarItem(selected = selectedView == index,
                        onClick = {
                            navigator.navigate(route = view.displayName)
                            selectedView = index
                        },
                        icon = {
                            Icon(view.displayIcon, view.displayName)
                        },
                        modifier = Modifier.padding(0.dp),
                        enabled = true,
                        label = { Text(view.displayName) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(),
                        interactionSource = null
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbar) },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        content = { views[selectedView].View(scope, snackbar) }
    )
}
