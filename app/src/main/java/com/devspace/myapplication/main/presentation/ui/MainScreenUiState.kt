package com.devspace.myapplication.main.presentation.ui

data class MainScreenUiState(
    val list: List<RecipeUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong",
)

data class RecipeUiData(
    val id: Int,
    val title: String,
    val summary: String,
    val image: String,
)