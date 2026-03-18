package com.knguy578.myapplication.data.local.mapper

import com.knguy578.myapplication.data.local.entity.MealEntity
import com.knguy578.myapplication.tracker.Meal

fun MealEntity.toUiMeal(): Meal = Meal(
    id = id,
    name = name,
    amountDescription = amountDescription,
    calories = calories,
    reason = reason,
    date = date
)

fun Meal.toEntity(): MealEntity = MealEntity(
    id = id,
    name = name,
    amountDescription = amountDescription,
    calories = calories,
    reason = reason,
    date = date
)

