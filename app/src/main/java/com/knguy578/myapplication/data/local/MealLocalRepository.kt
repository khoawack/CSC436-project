package com.knguy578.myapplication.data.local

import com.knguy578.myapplication.data.local.dao.MealDao
import com.knguy578.myapplication.data.local.mapper.toEntity
import com.knguy578.myapplication.data.local.mapper.toUiMeal
import com.knguy578.myapplication.tracker.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MealLocalRepository(
    private val mealDao: MealDao
) {

    fun observeMeals(): Flow<List<Meal>> {
        return mealDao.observeAllMeals().map { entities ->
            entities.map { it.toUiMeal() }
        }
    }

    suspend fun insertMeal(meal: Meal) {
        mealDao.insertMeal(meal.toEntity())
    }

    suspend fun updateMeal(meal: Meal) {
        mealDao.updateMeal(meal.toEntity())
    }

    suspend fun deleteMeal(mealId: String) {
        mealDao.deleteMealById(mealId)
    }
}

