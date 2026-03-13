package com.knguy578.myapplication.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knguy578.myapplication.data.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class TrackerViewModel : ViewModel() {

    private val repository = MealRepository()

    private val _uiState = MutableStateFlow(TrackerUiState())
    val uiState: StateFlow<TrackerUiState> = _uiState

    fun selectDate(date: LocalDate) {
        _uiState.update { current ->
            current.copy(selectedDate = date)
        }
    }

    fun analyzeMealAndAdd(name: String, amount: String) {
        viewModelScope.launch {

            setLoading(true)
            setError(null)

            val result = repository.analyzeMeal(name, amount)

            if (result?.cals != null) {
                addMeal(
                    name = name,
                    amount = amount,
                    calories = result.cals,
                    reason = result.reasoning ?: ""
                )
            } else {
                setError("Failed to analyze meal")
            }

            setLoading(false)
        }
    }

    fun addMeal(
        name: String,
        amount: String,
        calories: Int,
        reason: String
    ) {
        val currentDate = _uiState.value.selectedDate

        val newMeal = Meal(
            id = UUID.randomUUID().toString(),
            name = name,
            amountDescription = amount,
            calories = calories,
            reason = reason,
            date = currentDate
        )

        _uiState.update { current ->
            val updatedMealsForDate =
                (current.mealsByDate[currentDate] ?: emptyList()) + newMeal

            current.copy(
                mealsByDate = current.mealsByDate + (currentDate to updatedMealsForDate)
            )
        }
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.update { current ->
            current.copy(isLoading = isLoading)
        }
    }

    fun setError(message: String?) {
        _uiState.update { current ->
            current.copy(errorMessage = message)
        }
    }

    fun removeMeal(mealId: String) {
        val currentDate = _uiState.value.selectedDate

        _uiState.update { current ->
            val updatedMeals =
                current.mealsByDate[currentDate]
                    ?.filterNot { it.id == mealId }
                    ?: emptyList()

            current.copy(
                mealsByDate = current.mealsByDate + (currentDate to updatedMeals)
            )
        }
    }

    fun updateMeal(updatedMeal: Meal) {
        _uiState.update { current ->
            val date = updatedMeal.date

            val updatedList =
                current.mealsByDate[date]
                    ?.map { if (it.id == updatedMeal.id) updatedMeal else it }
                    ?: emptyList()

            current.copy(
                mealsByDate = current.mealsByDate + (date to updatedList)
            )
        }
    }
}