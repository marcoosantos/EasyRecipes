package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uiDetailScreen = MutableStateFlow<RecipeDto?>(null)
    val uiDetailScreen: StateFlow<RecipeDto?> = _uiDetailScreen

    fun fetchRecipeDetail(id: String) {
        if (_uiDetailScreen.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = detailService.getRecipeInformation(id)
                if (response.isSuccessful) {
                    _uiDetailScreen.value = response.body()
                } else {
                    Log.d(
                        "RecipeDetailViewModel",
                        "Request Error :: ${response.errorBody()}"
                    )
                }
            }
        }
    }

    fun cleanRecipeId() {
        viewModelScope.launch {
            delay(1000)
            _uiDetailScreen.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return RecipeDetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}