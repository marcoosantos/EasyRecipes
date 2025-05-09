package com.devspace.myapplication.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.model.SearchRecipeDto
import com.devspace.myapplication.search.data.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val searchService: SearchService
) : ViewModel() {


    private val _uiSearchScreen = MutableStateFlow<List<SearchRecipeDto>>(emptyList())
    val uiSearchScreen: StateFlow<List<SearchRecipeDto>> = _uiSearchScreen


    fun fetchSearchRecipe(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchService.searchRecipes(query)
            if (response.isSuccessful) {
                _uiSearchScreen.value = response.body()?.results ?: emptyList()
            } else {
                Log.d("SearchRecipeViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchService = RetrofitClient.retrofitInstance.create(SearchService::class.java)
                return SearchRecipeViewModel(
                    searchService
                ) as T
            }
        }
    }
}
