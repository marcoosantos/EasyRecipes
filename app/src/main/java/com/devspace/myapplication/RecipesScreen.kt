package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import designsystem.components.ERSearchBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipesScreen(navHostController: NavHostController) {

    var recipes by rememberSaveable { mutableStateOf<List<RecipeDto>>(emptyList()) }
    val retrofit = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    if (recipes.isEmpty()) {
        retrofit.getRandomRecipes().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    recipes = response.body()?.recipes ?: emptyList()
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")

                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }

        })
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MainContent(
            recipes = recipes,
            onSearchClicked = { query ->
                val tempCleanQuery = query.trim()
                if (tempCleanQuery.isNotEmpty()) {
                    navHostController.navigate(route = "search_recipes/?tempCleanQuery")
                }
            },
            onClick =
            { itemClicked ->
                navHostController.navigate(route = "recipe_detail/${itemClicked.id}")
            }
        )

    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    recipes: List<RecipeDto>,
    onSearchClicked: (String) -> Unit,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        var query by remember { mutableStateOf("") }
        SearchSession(
            label = "Find best recipes \nfor cooking",
            query = query,
            onValueChange = { newValue ->
                query = newValue
            },
            onSearchClicked = onSearchClicked
        )

        RecipeSession(
            label = "Recipes",
            recipeList = recipes,
            onCLick = onClick
        )
    }
}

@Composable
fun SearchSession(
    label: String,
    query: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )

    ERSearchBar(
        query = query,
        placeHolder = "Search recipes",
        onValueChange = onValueChange,
        onSearchClicked = {
            onSearchClicked.invoke(query)
        }
    )
}

@Composable
fun RecipeSession(
    label: String,
    recipeList: List<RecipeDto>,
    onCLick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            text = label,
            fontWeight = SemiBold
        )
        Spacer(modifier = Modifier.size(8.dp))
        RecipeList(recipeList = recipeList, onCLick = onCLick)
    }
}

@Composable
fun RecipeList(
    recipeList: List<RecipeDto>,
    onCLick: (RecipeDto) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(recipeList) {
            RecipeItem(
                recipeDto = it,
                onCLick = onCLick,
            )
        }
    }
}

@Composable
fun RecipeItem(
    recipeDto: RecipeDto,
    onCLick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onCLick.invoke(recipeDto)
            }
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            model = recipeDto.image,
            contentDescription = "${recipeDto.title} Recipe image"
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            fontWeight = SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = recipeDto.title
        )
        Text(
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            text = HtmlCompat.fromHtml(recipeDto.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    EasyRecipesTheme {
        MainContent(
            recipes = emptyList(),
            onSearchClicked = {

            },
            onClick = {

            }
        )
    }
}