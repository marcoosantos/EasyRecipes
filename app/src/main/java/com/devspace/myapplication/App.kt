package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.detail.presentation.ui.RecipeDetailScreen
import com.devspace.myapplication.list.presentation.ui.RecipesScreen
import com.devspace.myapplication.onboarding.ui.OnboardingScreen
import com.devspace.myapplication.search.presentation.ui.SearchRecipesScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding_screen" ){
        composable(route = "onboarding_screen"){
            OnboardingScreen(navController)
        }
        composable(route = "main_screen"){
            RecipesScreen(navController)
        }

        composable(
            route = "recipe_detail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(id, navController)
        }
        composable(
            route = "search_recipes" + "/{query}",
            arguments = listOf(navArgument("query"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(id, navController)
        }
    }
}