package org.example.myrecipes.core.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.myrecipes.database.RecipesDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(RecipesDatabase.Schema, "recipes.db")
    }
}