package com.example.royaal.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.royaal.commonui.LoadingPlaceholder

@Composable
fun LoadingScreen() {
    LoadingPlaceholder()
}

@Composable
fun ErrorScreen(
    e: Throwable
) {
    Text("Error - ${e.message}")
}