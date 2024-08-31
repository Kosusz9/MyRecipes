package org.example.myrecipes.recipes.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.myrecipes.core.presentation.ImagePicker
import org.example.myrecipes.recipes.domain.Recipe
import org.example.myrecipes.recipes.presentation.RecipeListEvent
import org.example.myrecipes.recipes.presentation.RecipeListState

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    newRecipe: Recipe?,
    onEvent: (RecipeListEvent) -> Unit,
    imagePicker: ImagePicker
) {
    imagePicker.registerPicker { imageBytes ->
        onEvent(RecipeListEvent.OnPhotoPicked(imageBytes))
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(RecipeListEvent.OnAddNewRecipeClick)
                },
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.NoteAdd,
                    contentDescription = "Dodaj przepis"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if(state.recentlyAddedRecipes.isNotEmpty()) {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        RecentlyAddedRecipes(
                            recipes = state.recentlyAddedRecipes,
                            onClick = {
                                onEvent(RecipeListEvent.SelectRecipe(it))
                            }
                        )
                    }
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = "Moje przepisy (${state.recipes.size})",
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                }
                items(state.recipes) { recipe ->
                    RecipeListItem(
                        recipe = recipe,
                        modifier = Modifier
                            .clickable {
                                onEvent(RecipeListEvent.SelectRecipe(recipe))
                            }
                    )
                }
            }
        }
    }

    AddRecipeSheet(
        state = state,
        newRecipe = newRecipe,
        isOpen = state.isAddRecipeSheetOpen,
        onEvent = { event ->
            if(event is RecipeListEvent.OnAddPhotoClicked) {
                imagePicker.pickImage()
            }
            onEvent(event)
        },
    )

    RecipeDetailSheet(
        isOpen = state.isSelectedRecipeSheetOpen,
        selectedRecipe = state.selectedRecipe,
        onEvent = onEvent
    )
}