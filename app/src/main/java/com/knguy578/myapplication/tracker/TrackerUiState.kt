package com.knguy578.myapplication.tracker

import java.time.LocalDate

data class TrackerUiState(
    val mealsByDate: Map<LocalDate, List<Meal>> = emptyMap(),
    val selectedDate: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val mealsForSelectedDate: List<Meal>
        get() = mealsByDate[selectedDate] ?: emptyList()

    val totalCaloriesForSelectedDate: Int
        get() = mealsForSelectedDate.sumOf { it.calories }
}

data class Meal(
    val id: String,
    val name: String,
    val amountDescription: String,
    val calories: Int,
    val reason: String,
    val date: LocalDate
)