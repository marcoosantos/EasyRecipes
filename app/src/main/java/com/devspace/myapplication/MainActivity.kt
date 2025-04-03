package com.devspace.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.devspace.myapplication.detail.presentation.RecipeDetailViewModel
import com.devspace.myapplication.main.presentation.MainScreenViewModel
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainScreenViewModel> { MainScreenViewModel.Factory }
    private val recipeDetailViewModel by viewModels<RecipeDetailViewModel> { RecipeDetailViewModel.Factory }
    private val searchRecipeViewModel by viewModels<SearchRecipeViewModel> { SearchRecipeViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = true

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = androidx.compose.ui.graphics.Color.White,
                        darkIcons = useDarkIcons
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                App(
                    mainScreenViewModel = mainViewModel,
                    recipeDetailViewModel = recipeDetailViewModel,
                    searchRecipeViewModel = searchRecipeViewModel
                )
            }

        }
    }
}