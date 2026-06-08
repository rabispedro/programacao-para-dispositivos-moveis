package com.example.a0xc0ffee.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

interface View {
    val displayName: String
    val displayIcon: ImageVector
    val tintColor: Color

    @Composable
    fun View()
}