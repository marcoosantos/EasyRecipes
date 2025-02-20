package com.devspace.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//Utilizar Json formatter
//link APIs:
//https://spoonacular.com/food-api/docs
//fazer lista de receitas aleatorias primeiro


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {
                var randomRecipes by remember { mutableStateOf<List<RecipeDto>>(emptyList()) }

                val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
                val callRandomRecipes = apiService.getRandomRecipes()

                callRandomRecipes.enqueue(object : Callback<RecipeResponse> {
                    override fun onResponse(
                        call: Call<RecipeResponse>,
                        response: Response<RecipeResponse>
                    ) {
                        if (response.isSuccessful) {
                            val recipes = response.body()?.recipes
                            if (recipes != null) {
                                randomRecipes = recipes
                            }
                        } else {
                            Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                        Log.d("MainActivity", "Network Error :: ${t.message}")
                    }

                })

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn {
                        items(randomRecipes) {
                            Text(text = it.title)
                        }
                    }

                }
            }
        }
    }
}