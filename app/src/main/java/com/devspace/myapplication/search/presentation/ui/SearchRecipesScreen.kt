package com.devspace.myapplication.search.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import com.devspace.myapplication.common.model.SearchRecipeDto
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel

@Composable
fun SearchRecipesScreen(
    query: String,
    navHostController: NavHostController,
    searchRecipeViewModel: SearchRecipeViewModel) {

    val searchRecipes by searchRecipeViewModel.uiSearchScreen.collectAsState()
    searchRecipeViewModel.fetchSearchRecipe(query)


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = query
            )
        }
        SearchRecipesContent(recipes = searchRecipes, onCLick = { itemClicked ->
            navHostController.navigate(route = "recipe_detail/${itemClicked.id}")
        })
    }
}

@Composable
private fun SearchRecipesContent(
    recipes: List<SearchRecipeDto>,
    onCLick: (SearchRecipeDto) -> Unit
) {
    SearchRecipesList(recipes, onCLick)
}

@Composable
fun SearchRecipesList(
    recipes: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(recipes) {
            SearchRecipeItem(searchRecipeDto = it, onCLick = onClick)
        }
    }
}


@Composable
private fun SearchRecipeItem(
    searchRecipeDto: SearchRecipeDto,
    onCLick: (SearchRecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onCLick.invoke(searchRecipeDto)
            }
    ) {

        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            model = searchRecipeDto.image, contentDescription = "${searchRecipeDto.title} Image"
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            text = searchRecipeDto.title
        )
    }
}