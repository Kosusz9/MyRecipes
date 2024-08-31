package org.example.myrecipes.recipes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.myrecipes.core.presentation.BottomSheet
import org.example.myrecipes.recipes.domain.Recipe
import org.example.myrecipes.recipes.presentation.RecipeListEvent

@Composable
fun RecipeDetailSheet(
    isOpen: Boolean,
    selectedRecipe: Recipe?,
    onEvent: (RecipeListEvent) -> Unit,
    modifier: Modifier = Modifier
    ) {

    var isFavorite by remember(selectedRecipe?.id) { mutableStateOf(selectedRecipe?.isFavorite ?: false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val listOfNutrients = listOfNotNull(
        selectedRecipe?.calories?.let { "Kalorie: ${it}kcal" },
        selectedRecipe?.proteins?.let { "Białko: ${it}g" },
        selectedRecipe?.fats?.let { "Tłuszcze: ${it}g" },
        selectedRecipe?.carbs?.let { "Węglowodany: ${it}g" }
    )

    BottomSheet(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(60.dp))
                RecipePhoto(
                    recipe = selectedRecipe,
                    iconSize = 150.dp,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "${selectedRecipe?.title}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Spacer(Modifier.height(16.dp))
                EditRow(
                    onEditClick = {
                        selectedRecipe?.let {
                            onEvent(RecipeListEvent.EditRecipe(it))
                        }
                    },
                    onDeleteClick = {
                        showDeleteDialog = true
                    },
                    onFavoriteClick = {
                        isFavorite = !isFavorite
                        selectedRecipe?.let { recipe ->
                            recipe.isFavorite = isFavorite
                            onEvent(RecipeListEvent.AddOrRemoveRecipeFromFavorite)
                        }
                    },
                    iconForFavorite = if(isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
                )
                Spacer(Modifier.height(16.dp))
                if(selectedRecipe?.description != null) {
                    RecipeInfoSection(
                        title = "Opis",
                        value = selectedRecipe.description,
                        icon = Icons.Rounded.Info,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(16.dp))
                RecipeInfoSection(
                    title = "Przygotowanie",
                    value = selectedRecipe?.preparation ?: "",
                    icon = Icons.Rounded.Info,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                RecipeInfoSection(
                    title = "Składniki",
                    value = selectedRecipe?.ingredients ?: "",
                    icon = Icons.Rounded.Info,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                if(listOfNutrients.isNotEmpty()) {
                    RecipeNutrientsSection(
                        listOfNutrients = listOfNutrients
                    )
                }
            }
            FilledTonalIconButton(
                onClick = {
                    onEvent(RecipeListEvent.DismissRecipe)
                },
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Zamknij"
                )
            }
        }
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Usuń przepis",
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                title = {
                    Text(text = "Potwierdź usunięcie")
                },
                text = {
                    Text(text = "Czy na pewno chcesz usunąć przepis?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEvent(RecipeListEvent.DeleteRecipe)
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Potwierdź")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Anuluj")
                    }
                }
            )
        }
    }
}

@Composable
private fun EditRow(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconForFavorite: ImageVector
) {
    Row(modifier) {
        FilledTonalIconButton(
            onClick = onEditClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edytuj przepis"
            )
        }
        FilledTonalIconButton(
            onClick = onDeleteClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Usuń przepis"
            )
        }
        FilledTonalIconButton(
            onClick = onFavoriteClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Icon(
                imageVector = iconForFavorite,
                contentDescription = "Dodaj lub usuń przepis z ulubionych"
            )
        }
    }
}

@Composable
private fun RecipeInfoSection(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Sekcja informacji",
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp
            )
            Text(
                text = value,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun RecipeNutrientsSection(
    listOfNutrients: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Sekcja informacji",
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Wartości odżywcze w 100g",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp
            )
            listOfNutrients.forEach { nutrient ->
                Text(
                    text = nutrient,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp
                )
            }
        }
    }
}