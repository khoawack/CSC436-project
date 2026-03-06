package com.knguy578.myapplication.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditMealSheetContent(
    meal: Meal,
    onSave: (Meal) -> Unit,
    onDelete: (String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(meal.name) }
    var caloriesText by remember { mutableStateOf(meal.calories.toString()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Edit Meal",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Meal Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = caloriesText,
            onValueChange = { caloriesText = it },
            label = { Text("Calories") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { onDelete(meal.id) }
            ) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }

            Row {
                TextButton(onClick = onCancel) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        val calories = caloriesText.toIntOrNull() ?: return@Button

                        val updatedMeal = meal.copy(
                            name = name,
                            calories = calories
                        )

                        onSave(updatedMeal)
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}