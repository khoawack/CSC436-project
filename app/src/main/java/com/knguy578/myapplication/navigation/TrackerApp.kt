package com.knguy578.myapplication.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.knguy578.myapplication.calendar.CalendarScreen
import kotlinx.serialization.Serializable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knguy578.myapplication.tracker.AddMealDialog
import com.knguy578.myapplication.tracker.EditMealDialog
import com.knguy578.myapplication.tracker.TrackerScreen
import com.knguy578.myapplication.tracker.TrackerViewModel
import com.knguy578.myapplication.tracker.Meal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

sealed class TrackerRoutes {

    @Serializable
    data object Home

    @Serializable
    data object Calendar

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerApp(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val trackerViewModel: TrackerViewModel = viewModel()
    val uiState by trackerViewModel.uiState.collectAsState()
    val selectedDate = uiState.selectedDate
    val today = LocalDate.now()

    val snackbarHostState = remember { SnackbarHostState() }

    val titleText = if (selectedDate == today) {
        "Today"
    } else {
        selectedDate.format(
            DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault())
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showAddMealSheet by remember { mutableStateOf(false) }
    var mealBeingEdited by remember { mutableStateOf<Meal?>(null) }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            trackerViewModel.setError(null)
        }
    }

    LaunchedEffect(uiState.isLoading) {
        if (!uiState.isLoading && showAddMealSheet) {
            showAddMealSheet = false
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (currentRoute == TrackerRoutes.Home::class.qualifiedName) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(titleText)
                            Text("${uiState.totalCaloriesForSelectedDate} calories")}
                    }
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            if (currentRoute != TrackerRoutes.Calendar::class.qualifiedName) {
                                navController.navigate(TrackerRoutes.Calendar) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Calendar"
                        )
                    }

                    IconButton(
                        onClick = {

                            if (currentRoute == TrackerRoutes.Calendar::class.qualifiedName) {
                                // if in calendar -> go back to today first
                                trackerViewModel.selectDate(LocalDate.now())

                                navController.navigate(TrackerRoutes.Home) {
                                    launchSingleTop = true
                                }
                            }

                            showAddMealSheet = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }

                    IconButton(
                        onClick = {
                            // reset to today's date
                            trackerViewModel.selectDate(LocalDate.now())

                            if (currentRoute != TrackerRoutes.Home::class.qualifiedName) {
                                navController.navigate(TrackerRoutes.Home) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Today"
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = TrackerRoutes.Home,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable<TrackerRoutes.Home> {
                TrackerScreen(
                    modifier = Modifier.fillMaxSize(),
                    trackerViewModel = trackerViewModel,
                    onEditMeal = { meal ->
                        mealBeingEdited = meal
                    }
                )
            }

            composable<TrackerRoutes.Calendar> {
                CalendarScreen(
                    onDateSelected = { date ->
                        trackerViewModel.selectDate(date)
                        navController.navigate(TrackerRoutes.Home) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

    if (showAddMealSheet) {
        AddMealDialog(
            isLoading = uiState.isLoading,
            onDismiss = {
                if (!uiState.isLoading) {
                    showAddMealSheet = false
                }
            },
            onSave = { name, amount ->
                trackerViewModel.analyzeMealAndAdd(name, amount)
            }
        )
    }

    if (mealBeingEdited != null) {
        EditMealDialog(
            meal = mealBeingEdited!!,
            onDismiss = { mealBeingEdited = null },
            onSave = { updatedMeal ->
                trackerViewModel.updateMeal(updatedMeal)
                mealBeingEdited = null
            },
            onDelete = { mealId ->
                trackerViewModel.removeMeal(mealId)
                mealBeingEdited = null
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackerAppPreview() {
    MaterialTheme {
        TrackerApp()
    }
}