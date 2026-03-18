# trackED (CSC 436 Project)

## Project Description
trackED is an Android calorie-tracking app built with Jetpack Compose that helps users log meals by day, estimate calories from meal descriptions using a remote analysis API, and review their intake history in both a daily list and a calendar view. Users can add, edit, and delete meals, then view total calories for the selected day and quick per-day calorie summaries over time.

## Figma Design
- Figma link: **https://www.figma.com/design/bECxzciV3NU387ezUDrVgx/CSC-436?node-id=1-5&t=6UXKiyJ9AnTrjfC3-0**
- Optional exported screens: place PNGs in `docs/figma/` and reference them here.

## Android + Jetpack Compose Features Used
- **Jetpack Compose UI** for all screens and reusable components (`Material 3`, `LazyColumn`, `LazyVerticalGrid`, dialogs, scaffold/top and bottom bars).
- **Navigation Compose** for in-app routing between Home and Calendar screens.
- **ViewModel + StateFlow** for state management and reactive UI updates.
- **Room** local database persistence for meal history:
  - Entity: `MealEntity`
  - DAO: `MealDao`
  - Database: `AppDatabase`
  - Type converter: `LocalDateTypeConverter`
- **Kotlin Coroutines + Flow** for async operations and database observation.
- **Retrofit + Gson** for network calls to meal-analysis backend.
- **Kotlin Serialization** support for serializable route objects.

## Third-Party Libraries / Notable Dependencies
- `androidx.room:room-runtime`, `androidx.room:room-ktx`, `androidx.room:room-compiler` (via KSP)
- `androidx.navigation:navigation-compose`
- `com.squareup.retrofit2:retrofit`
- `com.squareup.retrofit2:converter-gson`
- `org.jetbrains.kotlinx:kotlinx-coroutines-android`
- `org.jetbrains.kotlinx:kotlinx-serialization-json`

## Device / SDK Requirements
- **minSdk**: 24 (Android 7.0)
- **targetSdk**: 36
- **compileSdk**: 36
- Internet connection is required for meal analysis API requests.
- Java 11 toolchain is configured in Gradle.

## Setup and Run
1. Open this project folder in Android Studio.
2. Sync Gradle files.
3. Run the `app` configuration on an emulator or Android device (API 24+).

## What to Call Out (Above and Beyond)
- Built and deployed a custom Python FastAPI backend that integrates with the OpenAI API for meal analysis. Link to that repo: https://github.com/khoawack/fast-api-csc-436
- Persisted local meal history with Room and calendar/day-level summaries.
- Careful consideration for project code structure.

