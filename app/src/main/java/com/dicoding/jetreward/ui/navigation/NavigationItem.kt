package com.dicoding.jetreward.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)
