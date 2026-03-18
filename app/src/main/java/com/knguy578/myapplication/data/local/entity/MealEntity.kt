package com.knguy578.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "meals",
    indices = [Index(value = ["date"])]
)
data class MealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val amountDescription: String,
    val calories: Int,
    val reason: String,
    val date: LocalDate
)

