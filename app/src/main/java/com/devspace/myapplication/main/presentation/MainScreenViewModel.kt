package com.devspace.myapplication.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.main.data.MainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val mainService: MainService
) : ViewModel() {

    private val _uiMainScreen = MutableStateFlow<List<RecipeDto>>(emptyList())
    val uiMainScreen: StateFlow<List<RecipeDto>> = _uiMainScreen

    init {
        fetchMainScreen()

    }

    private fun fetchMainScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainService.getRandomRecipes()
            if (response.isSuccessful) {
                _uiMainScreen.value = response.body()?.recipes ?: emptyList()
            } else {
                Log.d(
                    "MainScreenViewModel",
                    "Request Error :: ${response.errorBody()}"
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val mainService = RetrofitClient.retrofitInstance.create(MainService::class.java)
                return MainScreenViewModel(
                    mainService
                ) as T
            }
        }
    }
}