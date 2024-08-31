package org.example.myrecipes.recipes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.myrecipes.recipes.domain.Recipe

@Composable
fun RecipeListItem(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RecipePhoto(
            recipe = recipe,
            modifier = Modifier.size(150.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            textAlign = TextAlign.Center,
            text = recipe.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}