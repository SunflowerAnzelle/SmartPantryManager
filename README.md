\# Smart Pantry Manager



\## About the Application



Smart Pantry Manager is an Android application developed in Java using Android Studio. It helps users keep track of ingredients in their pantry and find recipes that can be prepared using what they already have.



The main idea behind the application is to make it easier to manage pantry ingredients and use food that is already available before buying more.



\## Main Features



The application allows users to:



\* Add pantry ingredients

\* Edit existing pantry ingredients

\* Delete pantry ingredients

\* Store ingredient quantities and units

\* Store optional expiry dates

\* View pantry items using a RecyclerView

\* Scroll through the pantry list when there are multiple items

\* Automatically display pantry item numbers based on their current position

\* Store pantry data locally using SQLite

\* View suggested recipes based on the current pantry contents

\* Match recipes using available ingredients and quantities

\* Handle supported unit differences

\* Handle simple singular and plural ingredient names

\* View recipe ingredients and preparation instructions

\* Access application settings

\* Validate pantry input

\* Receive feedback when no recipes can currently be prepared



\## Technologies Used



\* \*\*Programming Language:\*\* Java

\* \*\*Development Environment:\*\* Android Studio

\* \*\*Database:\*\* SQLite

\* \*\*User Interface:\*\* Android XML layouts

\* \*\*List Display:\*\* RecyclerView

\* \*\*Data Display:\*\* Custom RecyclerView adapters

\* \*\*Navigation:\*\* Android activity navigation



\## Database



Smart Pantry Manager uses \*\*SQLite\*\* for local data storage.



SQLite was chosen because the application is designed to store pantry information directly on the user's device. The application does not need an online database or a separate server for its main functions.



Pantry information can therefore be saved locally and loaded again when the application is reopened. SQLite also provides the database operations needed to add, update, retrieve and delete pantry information.



The database is managed through the application's `DatabaseHelper` class.



\## Application Structure



The main activities in the application are:



\* \*\*MainActivity\*\* - Home screen and main navigation

\* \*\*PantryActivity\*\* - Displays pantry ingredients

\* \*\*AddEditPantryActivity\*\* - Adds and edits pantry ingredients

\* \*\*RecipeActivity\*\* - Displays recipes that can currently be prepared

\* \*\*RecipeDetailActivity\*\* - Displays recipe ingredients and preparation steps

\* \*\*SettingsActivity\*\* - Application settings



The Java source files are located in:



```text

app/src/main/java/com/example/smartpantrymanager/

```



The Android layouts and other resources are located in:



```text

app/src/main/res/

```



\## How to Run the Application



1\. Install Android Studio and make sure an Android SDK is available.

2\. Clone or download this repository.

3\. Open the project in Android Studio.

4\. Allow Android Studio to finish synchronising the Gradle project.

5\. Start an Android Emulator or connect an Android device with USB debugging enabled.

6\. Select the `app` configuration in Android Studio.

7\. Run the application.



After the application starts, ingredients can be added from the Pantry section. The Suggested Recipes section can then be used to check which recipes can be prepared from the current pantry contents.



The SQLite database is created and managed by the application when it is run.



\## Strict Recipe Matching



The application uses strict recipe matching. A recipe is only displayed in the main Suggested Recipes list when all of its required ingredients are available in the pantry in sufficient quantities.



For example, if a recipe requires five eggs but only four eggs are available, the recipe will not be suggested.



The matching process also accounts for supported unit conversions and simple differences such as singular and plural ingredient names.



\## Testing



The main application features were tested using an Android Emulator.



Testing included pantry CRUD operations, input validation, pantry list scrolling, automatic item numbering, SQLite data persistence, recipe matching, recipe details and navigation.



More detailed testing information is available in:



```text

docs/TESTING.md

```



\## Project Purpose



Smart Pantry Manager was developed as part of the Mobile App Development 700 practical assignment.



The project was created to apply Android development concepts including Java programming, Android activities and layouts, RecyclerView, SQLite database storage, input validation and application testing.



