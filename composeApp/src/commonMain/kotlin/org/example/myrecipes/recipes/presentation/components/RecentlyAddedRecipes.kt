package org.example.myrecipes.recipes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.myrecipes.recipes.domain.Recipe

@Composable
fun RecentlyAddedRecipes(
    recipes: List<Recipe>,
    onClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Ostatnio dodane",
            modifier = Modifier
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes) { recipe ->
                RecipePreviewItem(
                    recipe = recipe,
                    onClick = { onClick(recipe) }
                )
            }
        }
    }
}