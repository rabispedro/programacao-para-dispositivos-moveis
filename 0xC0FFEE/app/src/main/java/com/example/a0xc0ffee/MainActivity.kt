package com.example.a0xc0ffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a0xc0ffee.controller.ClienteController
import com.example.a0xc0ffee.controller.PedidoController
import com.example.a0xc0ffee.controller.ProdutoController
import com.example.a0xc0ffee.controller.RelatorioController
import com.example.a0xc0ffee.view.ClienteView
import com.example.a0xc0ffee.view.PedidoView
import com.example.a0xc0ffee.view.ProdutoView
import com.example.a0xc0ffee.view.RelatorioView
import com.example.a0xc0ffee.view.View

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            val produtoController = ProdutoController(navController)
            val clienteController = ClienteController(navController)
            val pedidoController = PedidoController(navController)
            val relatorioController = RelatorioController(navController)

            val clienteView = ClienteView(clienteController)
            val pedidoView = PedidoView(pedidoController)
            val produtoView = ProdutoView(produtoController)
            val relatorioView = RelatorioView(relatorioController)

            val controllers = listOf(produtoController, clienteController, pedidoController, relatorioController)
            val views = listOf(produtoView, clienteView, pedidoView, relatorioView)

            PageRouting(navController, views)
        }
    }
}

@Composable
fun PageRouting(navigator: NavHostController, views: List<View>) {
    var selectedView by remember { mutableIntStateOf(0) }

    NavHost(navigator, startDestination = "produto") {
        views.forEach { view ->
            composable(view.displayName) {
                view.View()
            }
        }
    }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        views.forEachIndexed { index, view ->
            NavigationBarItem(
                selected = selectedView == index,
                onClick = {
                    navigator.navigate(route = view.displayName)
                    selectedView = index
                },
                icon = { Icon(
                    view.displayIcon,
                    contentDescription = view.displayName,
                    modifier = Modifier,
                    tint = view.tintColor
                ) },
                modifier = Modifier.padding(0.dp),
                enabled = true,
                label = { Text(view.displayName) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(),
                interactionSource = null
            )
        }
    }
}
