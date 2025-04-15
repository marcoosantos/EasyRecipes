package com.devspace.myapplication.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.EasyRecipesApplication
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.main.data.MainScreenRepository
import com.devspace.myapplication.main.data.remote.MainService
import com.devspace.myapplication.main.presentation.ui.MainScreenUiState
import com.devspace.myapplication.main.presentation.ui.RecipeUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainScreenViewModel(
    private val repository: MainScreenRepository
) : ViewModel() {

    private val _uiMainScreen = MutableStateFlow(MainScreenUiState())
    val uiMainScreen: StateFlow<MainScreenUiState> = _uiMainScreen

    init {
        fetchMainScreen()
    }

    private fun fetchMainScreen() {
        _uiMainScreen.value = MainScreenUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRandomRecipes()
            if (response.isSuccess) {
                val recipes = response.getOrNull()
                if (recipes != null) {
                    val recipeUiDataList = recipes.map { RecipeDto ->
                        RecipeUiData(
                            id = RecipeDto.id,
                            title = RecipeDto.title,
                            summary = RecipeDto.summary,
                            image = RecipeDto.image
                        )
                    }
                    _uiMainScreen.value = MainScreenUiState(list = recipeUiDataList)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiMainScreen.value = MainScreenUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiMainScreen.value = MainScreenUiState(isError = true)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val mainService = RetrofitClient.retrofitInstance.create(MainService::class.java)
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainScreenViewModel(
                    repository = (application as EasyRecipesApplication).repository
                ) as T
            }
        }
    }
}