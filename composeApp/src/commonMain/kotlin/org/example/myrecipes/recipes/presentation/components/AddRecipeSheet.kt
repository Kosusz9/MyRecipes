package org.example.myrecipes.recipes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.myrecipes.core.presentation.BottomSheet
import org.example.myrecipes.recipes.domain.Recipe
import org.example.myrecipes.recipes.presentation.RecipeListEvent
import org.example.myrecipes.recipes.presentation.RecipeListState

@Composable
fun AddRecipeSheet(
    state: RecipeListState,
    newRecipe: Recipe?,
    isOpen: Boolean,
    onEvent: (RecipeListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheet(
        visible = isOpen,
        modifier = modifier
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
                if(newRecipe?.mainPhotoBytes == null) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(40))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable{
                                onEvent(RecipeListEvent.OnAddPhotoClicked)
                            }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                shape = RoundedCornerShape(40)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Dodaj zdjęcie",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    RecipePhoto(
                        recipe = newRecipe,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                onEvent(RecipeListEvent.OnAddPhotoClicked)
                            }
                    )
                }
                Spacer(Modifier.height(16.dp))
                RecipeTextField(
                    value = newRecipe?.title ?: "",
                    placeholder = "*Podaj nazwę przepisu",
                    error = state.titleError,
                    onValueChanged = {
                        onEvent(RecipeListEvent.OnTitleChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Text
                )
                Spacer(Modifier.height(16.dp))
                RecipeTextField(
                    value = newRecipe?.description ?: "",
                    placeholder = "Podaj opis przepisu",
                    error = null,
                    onValueChanged = { text ->
                        onEvent(RecipeListEvent.OnDescriptionChange(text.ifBlank { null }))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Text
                )
                Spacer(Modifier.height(16.dp))
                RecipeTextField(
                    value = newRecipe?.preparation ?: "",
                    placeholder = "*Podaj sposób przygotowania przepisu",
                    error = state.preparationError,
                    onValueChanged = {
                        onEvent(RecipeListEvent.OnPreparationChange(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Text
                )
                Spacer(Modifier.height(16.dp))
                RecipeTextField(
                    value = newRecipe?.ingredients ?: "",
                    placeholder = "*Podaj listę składników przepisu",
                    error = state.ingredientsError,
                    onValueChanged = {
                        onEvent(RecipeListEvent.OnIngredientsChange(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Text
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RecipeTextField(
                            value = (newRecipe?.calories ?: "").toString(),
                            placeholder = "Kalorie/100g",
                            error = null,
                            onValueChanged = {
                                onEvent(RecipeListEvent.OnCaloriesChange(it.toIntOrNull()))
                            },
                            keyboardType = KeyboardType.Number
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RecipeTextField(
                            value = (newRecipe?.proteins ?: "").toString(),
                            placeholder = "Białko/100g",
                            error = null,
                            onValueChanged = {
                                onEvent(RecipeListEvent.OnProteinsChange(it.toIntOrNull()))
                            },
                            keyboardType = KeyboardType.Number
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RecipeTextField(
                            value = (newRecipe?.fats ?: "").toString(),
                            placeholder = "Tłuszcze/100g",
                            error = null,
                            onValueChanged = {
                                onEvent(RecipeListEvent.OnFatsChange(it.toIntOrNull()))
                            },
                            keyboardType = KeyboardType.Number
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RecipeTextField(
                            value = (newRecipe?.carbs ?: "").toString(),
                            placeholder = "Węglowodany/100g",
                            error = null,
                            onValueChanged = {
                                onEvent(RecipeListEvent.OnCarbsChange(it.toIntOrNull()))
                            },
                            keyboardType = KeyboardType.Number
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick =  {
                        onEvent(RecipeListEvent.SaveRecipe)
                    }
                ) {
                    Text(text = "Zapisz")
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
                    contentDescription = "Zamknij",
                )
            }
        }
    }
}

@Composable
private fun RecipeTextField(
    value: String,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            placeholder = {
                Text(text = placeholder)
            },
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(20.dp),
            modifier = modifier,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        )
        if(error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}