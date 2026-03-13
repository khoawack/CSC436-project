package com.knguy578.myapplication.tracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.unit.dp

@Composable
fun EditMealDialog(
    meal: Meal,
    onDismiss: () -> Unit,
    onSave: (Meal) -> Unit,
    onDelete: (String) -> Unit
) {
    var name by rememberSaveable { mutableStateOf(meal.name) }
    var caloriesText by rememberSaveable { mutableStateOf(meal.calories.toString()) }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        onClick = { onDelete(meal.id) }
                    ) {
                        Text("Delete")
                    }

                    Button(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            val calories = caloriesText.toIntOrNull() ?: return@Button
                            onSave(meal.copy(name = name, calories = calories))
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}