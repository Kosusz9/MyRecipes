package org.example.myrecipes.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.myrecipes.ai.ChatAIClient

@Composable
fun AssistantScreen() {

    var text by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            Text(
                text = "Zapytaj sztuczną inteligencję o przepis na:",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }
        item {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Wprowadź nazwę potrawy") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                isLoading = true
                coroutineScope.launch {
                    response = ChatAIClient().getRecipeSuggestion(text)
                    isLoading = false
                }
            }) {
                Text("Zapytaj")
            }
        }
        item {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(text = response)
            }
        }
    }

}