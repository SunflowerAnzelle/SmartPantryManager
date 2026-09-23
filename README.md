# Smart Pantry Manager

## About the Application

Smart Pantry Manager is an Android application developed in Java using Android Studio. The application helps users keep track of ingredients stored in their pantry and suggests recipes that can be prepared using the ingredients currently available.

The application focuses on reducing food waste by helping users use ingredients they already have instead of purchasing additional ingredients unnecessarily.

## Main Features

* Add pantry ingredients
* Edit existing pantry ingredients
* Delete pantry ingredients
* Store ingredient quantities and units
* Store optional expiry dates
* View pantry items using a RecyclerView
* Store pantry data persistently using SQLite
* Suggest recipes based on the current pantry contents
* Strict recipe matching based on available ingredients and quantities
* Handle common unit differences
* Handle simple singular and plural ingredient names
* View recipe ingredients and preparation instructions
* Settings screen
* Input validation
* Feedback when no recipes can currently be prepared

## Technologies Used

* **Programming Language:** Java
* **Development Environment:** Android Studio
* **Database:** SQLite
* **UI:** Android XML layouts
* **List Display:** RecyclerView
* **Data Display:** Custom RecyclerView adapters
* **Navigation:** Android Intents and activity navigation

## Database

SQLite was selected because Smart Pantry Manager is a local Android application that primarily requires persistent storage on the user's device.

SQLite allows pantry and recipe data to be stored locally without requiring an external database server or internet connection. It also supports the CRUD operations required by the application.

## Application Structure

The application contains several activities:

* **MainActivity** – Home screen and main navigation
* **PantryActivity** – Displays pantry ingredients
* **AddEditPantryActivity** – Adds and edits pantry ingredients
* **RecipeActivity** – Displays recipes that can currently be prepared
* **RecipeDetailActivity** – Displays recipe ingredients and preparation steps
* **SettingsActivity** – Application settings

## How to Run the Application

1. Install Android Studio.
2. Clone or download this repository.
3. Open the project in Android Studio.
4. Allow Android Studio to synchronise the Gradle project.
5. Connect an Android device or start an Android Emulator.
6. Build and run the application.
7. Use the Pantry screen to add ingredients and quantities.
8. Open Suggested Recipes to view recipes that can be prepared from the available pantry items.

## Strict Recipe Matching

The application uses strict recipe matching. A recipe is only displayed in the main Suggested Recipes list when all of its required ingredients are available in the pantry in sufficient quantities.

For example, if a recipe requires five eggs but only four eggs are available, that recipe will not be suggested.

The matching process also accounts for simple real-world differences such as supported unit conversions and singular/plural ingredient names.

## Project Purpose

The purpose of Smart Pantry Manager is to provide a simple way for users to manage pantry ingredients and identify recipes that can be prepared from what they already have available.
