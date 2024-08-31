package org.example.myrecipes.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.myrecipes.database.RecipesDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            RecipesDatabase.Schema,
            context,
            "recipes.db"
        )
    }
}