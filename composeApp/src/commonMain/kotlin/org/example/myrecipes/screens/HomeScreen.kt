package org.example.myrecipes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.example.myrecipes.core.presentation.ImagePicker
import org.example.myrecipes.di.AppModule
import org.example.myrecipes.recipes.presentation.RecipeListViewModel
import org.example.myrecipes.recipes.presentation.components.RecipeListScreen

@Composable
fun HomeScreen(
    appModule: AppModule,
    imagePicker: ImagePicker
) {
    val viewModel = getViewModel(
        key = "recipe-list-screen",
        factory = viewModelFactory {
            RecipeListViewModel(appModule.recipeDataSource)
        }
    )
    val state by viewModel.state.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        RecipeListScreen(
            state = state,
            newRecipe = viewModel.newRecipe,
            onEvent = viewModel::onEvent,
            imagePicker = imagePicker
        )
    }
}