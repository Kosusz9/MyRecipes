package org.example.myrecipes.recipes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.myrecipes.recipes.domain.Recipe
import org.example.myrecipes.recipes.domain.RecipeDataSource
import org.example.myrecipes.recipes.domain.RecipeValidator

class RecipeListViewModel(
    private val recipeDataSource: RecipeDataSource
): ViewModel() {

    private val _state = MutableStateFlow(RecipeListState())
    val state = combine(
        _state,
        recipeDataSource.getRecipes(),
        recipeDataSource.getRecentRecipes(5),
        recipeDataSource.getFavoriteRecipes()
    ) { state, recipes, recentRecipes, favorite ->
        state.copy(
            recipes = recipes,
            recentlyAddedRecipes = recentRecipes,
            favoriteRecipes = favorite
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RecipeListState())

    var newRecipe: Recipe? by mutableStateOf(null)
        private set

    fun onEvent(event: RecipeListEvent) {
        when(event) {
            RecipeListEvent.AddOrRemoveRecipeFromFavorite -> {
                viewModelScope.launch {
                    _state.value.selectedRecipe?.id?.let { id ->
                        recipeDataSource.addOrRemoveRecipeFromFavorite(id)
                    }
                }
            }
            RecipeListEvent.DeleteRecipe -> {
                viewModelScope.launch {
                    _state.value.selectedRecipe?.id?.let { id ->
                        _state.update { it.copy(
                            isSelectedRecipeSheetOpen = false
                        ) }
                        recipeDataSource.deleteRecipe(id)
                        delay(300) // animation delay
                        _state.update { it.copy(
                            selectedRecipe = null
                        ) }
                    }
                }
            }
            RecipeListEvent.DismissRecipe -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isSelectedRecipeSheetOpen = false,
                        isAddRecipeSheetOpen = false,
                        titleError = null,
                        preparationError = null,
                        ingredientsError = null
                    ) }
                    delay(300)
                    newRecipe = null
                    _state.update { it.copy(
                        selectedRecipe = null
                    ) }
                }
            }
            is RecipeListEvent.EditRecipe -> {
                viewModelScope.launch {
                    newRecipe = event.recipe
                    _state.update { it.copy(
                        isSelectedRecipeSheetOpen = false,
                        isAddRecipeSheetOpen = true,

                    ) }
                    delay(500)
                    _state.update { it.copy(
                        selectedRecipe = null
                    ) }
                }
            }
            RecipeListEvent.OnAddNewRecipeClick -> {
                _state.update { it.copy(
                    isAddRecipeSheetOpen = true
                ) }
                newRecipe = Recipe(
                    id = null,
                    title = "",
                    description = null,
                    preparation = "",
                    ingredients = "",
                    calories = null,
                    proteins = null,
                    fats = null,
                    carbs = null,
                    mainPhotoBytes = null,
                    isFavorite = false
                )
            }
            is RecipeListEvent.OnCaloriesChange -> {
                newRecipe = newRecipe?.copy(
                    calories = event.value
                )
            }
            is RecipeListEvent.OnCarbsChange -> {
                newRecipe = newRecipe?.copy(
                    carbs = event.value
                )
            }
            is RecipeListEvent.OnDescriptionChange -> {
                newRecipe = newRecipe?.copy(
                    description = event.value
                )
            }
            is RecipeListEvent.OnFatsChange -> {
                newRecipe = newRecipe?.copy(
                    fats = event.value
                )
            }
            is RecipeListEvent.OnPreparationChange -> {
                _state.update { it.copy(
                    preparationError = null
                ) }
                newRecipe = newRecipe?.copy(
                    preparation = event.value
                )
            }
            is RecipeListEvent.OnIngredientsChange -> {
                _state.update { it.copy(
                    ingredientsError = null
                ) }
                newRecipe = newRecipe?.copy(
                    ingredients = event.value
                )
            }
            is RecipeListEvent.OnProteinsChange -> {
                newRecipe = newRecipe?.copy(
                    proteins = event.value
                )
            }
            is RecipeListEvent.OnTitleChanged -> {
                _state.update { it.copy(
                    titleError = null
                ) }
                newRecipe = newRecipe?.copy(
                    title = event.value
                )
            }
            is RecipeListEvent.OnPhotoPicked -> {
                newRecipe = newRecipe?.copy(
                    mainPhotoBytes = event.bytes
                )
            }
            RecipeListEvent.SaveRecipe -> {
                newRecipe?.let { recipe ->
                    val result = RecipeValidator.validateRecipe(recipe)
                    val errors = listOfNotNull(
                        result.titleError,
                        result.preparationError,
                        result.ingredientsError
                    )
                    if(errors.isEmpty()) {
                        _state.update { it.copy(
                            isAddRecipeSheetOpen = false,
                            titleError = null,
                            preparationError = null,
                            ingredientsError = null
                        ) }
                        viewModelScope.launch {
                            recipeDataSource.insertRecipe(recipe)
                            delay(300)
                            newRecipe = null
                        }
                    }
                    else {
                        _state.update { it.copy(
                            titleError = result.titleError,
                            preparationError = result.preparationError,
                            ingredientsError = result.ingredientsError
                        ) }
                    }
                }
            }
            is RecipeListEvent.SelectRecipe -> {
                _state.update { it.copy(
                    selectedRecipe = event.recipe,
                    isSelectedRecipeSheetOpen = true
                ) }
            }
            else -> Unit
        }
    }
}