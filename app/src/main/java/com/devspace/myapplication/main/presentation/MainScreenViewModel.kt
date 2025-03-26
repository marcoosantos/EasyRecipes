package com.devspace.myapplication.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.common.model.RecipeResponse
import com.devspace.myapplication.main.data.MainService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainScreenViewModel(
    private val mainService: MainService
) : ViewModel() {

    private val _uiMainScreen = MutableStateFlow<List<RecipeDto>>(emptyList())
    val uiMainScreen: StateFlow<List<RecipeDto>> = _uiMainScreen

    init {
        fetchMainScreen()

    }

    private fun fetchMainScreen(){
        mainService.getRandomRecipes().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    _uiMainScreen.value = response.body()?.recipes ?: emptyList()
                } else {
                    Log.d("MainScreenViewModel", "Request Error :: ${response.errorBody()}")

                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d("MainScreenViewModel", "Network Error :: ${t.message}")
            }
        })
    }

    companion object{
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
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