package com.knguy578.myapplication.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit = {}
) {

    val currentMonth = remember { YearMonth.now() }
    var selectedMonth by remember { mutableStateOf(currentMonth) }

    val today = LocalDate.now()

    Column(modifier = modifier.fillMaxSize()) {

        // month picker
        CalendarTopBar(
            selectedMonth = selectedMonth,
            onPrevious = {
                val previous = selectedMonth.minusMonths(1)
                // Prevent going into future
                if (previous <= currentMonth) {
                    selectedMonth = previous
                }
            },
            onNext = {
                val next = selectedMonth.plusMonths(1)
                if (next <= currentMonth) {
                    selectedMonth = next
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val daysInMonth = selectedMonth.lengthOfMonth()
        val dates = (1..daysInMonth).map {
            selectedMonth.atDay(it)
        }

        // days grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(dates) { date ->

                DayButton(
                    date = date,
                    isToday = date == today,
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}

@Composable
fun CalendarTopBar(
    selectedMonth: YearMonth,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {

    val monthName = selectedMonth.month.getDisplayName(
        TextStyle.FULL,
        Locale.getDefault()
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onPrevious) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
        }

        Text(
            text = "$monthName ${selectedMonth.year}",
            style = MaterialTheme.typography.titleLarge
        )

        IconButton(onClick = onNext) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
        }
    }
}

@Composable
fun DayButton(
    date: LocalDate,
    isToday: Boolean,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(6.dp)
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isToday)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = date.dayOfMonth.toString())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarScreenPreview() {
    MaterialTheme {
        CalendarScreen(
            onDateSelected = {}
        )
    }
}