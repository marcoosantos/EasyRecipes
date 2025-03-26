package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.detail.data.DetailService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uiDetailScreen = MutableStateFlow<RecipeDto?>(null)
    val uiDetailScreen: StateFlow<RecipeDto?> = _uiDetailScreen

    fun fetchRecipeDetail(id: String) {
        if (_uiDetailScreen.value == null) {
            detailService.getRecipeInformation(id).enqueue(object : Callback<RecipeDto> {
                override fun onResponse(call: Call<RecipeDto>, response: Response<RecipeDto>) {
                    if (response.isSuccessful) {
                        _uiDetailScreen.value = response.body()
                    } else {
                        Log.d("RecipeDetailViewModel", "Request Error :: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<RecipeDto>, t: Throwable) {
                    Log.d("RecipeDetailViewModel", "Network Error :: ${t.message}")
                }
            })
        }
    }

    fun cleanRecipeId(){
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