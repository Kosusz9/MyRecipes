package org.example.myrecipes.recipes.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.myrecipes.core.presentation.rememberBitmapFromBytes
import org.example.myrecipes.recipes.domain.Recipe

@Composable
fun RecipePhoto(
    recipe: Recipe?,
    modifier: Modifier = Modifier,
    iconSize: Dp = 100.dp
) {
    val bitmap = rememberBitmapFromBytes(recipe?.mainPhotoBytes)
    val photoModifier = modifier.clip(RoundedCornerShape(20))

    if(bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = recipe?.title,
            modifier = photoModifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = photoModifier.background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Receipt,
                contentDescription = recipe?.title,
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}