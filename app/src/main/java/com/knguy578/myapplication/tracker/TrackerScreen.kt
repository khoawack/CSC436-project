package com.knguy578.myapplication.tracker

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TrackerScreen(
    modifier: Modifier = Modifier,
    trackerViewModel: TrackerViewModel,
    onEditMeal: (Meal) -> Unit
) {
    val uiState by trackerViewModel.uiState.collectAsState()

    TrackerContent(
        modifier = modifier.fillMaxSize(),
        uiState = uiState,
        onEditMeal = onEditMeal
    )
}


@Composable
fun TrackerContent(
    modifier: Modifier = Modifier,
    uiState: TrackerUiState,
    onEditMeal: (Meal) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(uiState.mealsForSelectedDate) { meal ->
            CalorieCard(
                mealName = meal.name,
                calories = "${meal.calories} cals",
                reason = meal.reason,
                onEditClick = {
                    onEditMeal(meal)
                }
            )
        }
    }
}

@Composable
fun CalorieCard(
    modifier: Modifier = Modifier,
    mealName: String,
    calories: String,
    reason: String,
    onEditClick: () -> Unit = {}
) {

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // top row (meal + calories)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = mealName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp)
                )

                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = calories,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // reason (only visible when expanded)
            if (isExpanded) {
                Text(
                    text = reason,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // bottom row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isExpanded) "See less" else "See more",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        isExpanded = !isExpanded
                    }
                )

                Text(
                    text = "Edit",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onEditClick() }
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
private fun TrackerScreenPreview() {
    val fakeViewModel = TrackerViewModel()

    MaterialTheme {
        TrackerScreen(
            modifier = Modifier.fillMaxSize(),
            trackerViewModel = fakeViewModel,
            onEditMeal = {}
        )
    }
}