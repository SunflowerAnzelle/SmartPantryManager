# Smart Pantry Manager - Testing Documentation

## 1. Purpose

Testing was carried out during development to make sure that the main Smart Pantry Manager features work correctly and that information entered by the user is handled and stored correctly.

The application was tested using an Android Emulator. The testing focused mainly on pantry management, recipe suggestions, database persistence, input validation and navigation.

## 2. Pantry Testing

The pantry functions were tested by adding different ingredients and checking that they appeared correctly in the pantry list.

### Adding pantry items

A pantry item was added using valid information such as the ingredient name, quantity, unit and expiry date. The item was saved successfully and appeared in the pantry list.

The add function was also tested with missing information. When a required field was left empty, the application displayed a validation message and did not save the incomplete item.

Invalid quantity values were also tested. The application prevents invalid quantity input from being saved.

### Editing pantry items

Existing pantry items were selected and edited. After saving the changes, the updated information was displayed in the pantry list.

### Deleting pantry items

Pantry items were deleted individually to check that the selected item was removed from the list.

The numbering of the remaining items was also checked after deletion. The numbers are based on the current position of the item in the RecyclerView, so the remaining items are automatically renumbered.

### Pantry list scrolling

The pantry list was tested with multiple ingredients to make sure that the user can scroll through the list when there are more items than can be displayed on the screen at once.

The RecyclerView allows the user to move through the list and access all the stored pantry items.

## 3. Recipe Testing

The Suggested Recipes feature was tested using different pantry contents.

### Matching ingredients

Recipes were tested when the pantry contained all the required ingredients. Matching recipes were displayed in the Suggested Recipes section.

### Missing ingredients

A recipe was tested when one of its required ingredients was not available in the pantry. The recipe was excluded from the matching results.

This was used to confirm that the application does not suggest a recipe simply because some of its ingredients are available.

### Insufficient quantities

Recipe matching was also tested when the pantry contained an ingredient but did not contain enough of it.

For example, if a recipe requires a larger quantity than the amount stored in the pantry, the recipe is not included in the matching results.

### Unit conversion

Supported unit differences were tested during recipe matching. The application can handle supported unit conversions when comparing pantry quantities with recipe requirements.

### Singular and plural ingredient names

Simple differences such as singular and plural ingredient names were also tested so that matching ingredients are not incorrectly treated as completely different ingredients.

### No matching recipes

The application was tested when there were no recipes that matched the current pantry contents. Appropriate feedback was displayed instead of presenting an incorrect recipe match.

### Recipe details

A matching recipe was opened to test the recipe details screen. The recipe ingredients and preparation instructions were displayed correctly.

## 4. Database Persistence

SQLite persistence was tested by adding pantry ingredients and then closing and reopening the application.

The previously saved pantry information was still available after reopening the application. This confirmed that pantry information is stored in the local SQLite database rather than only being held temporarily while the application is running.

The pantry data was also checked after editing and deleting items to make sure that the changes were reflected correctly when the list was loaded again.

## 5. Input Validation

Input validation was tested to prevent incorrect pantry information from being saved.

The application was tested with:

- Missing required information
- Empty ingredient names
- Invalid quantity values
- Valid information

When invalid information was entered, the application provided feedback and prevented the incorrect data from being saved.

This helps prevent incomplete or invalid information from being added to the pantry database.

## 6. User Interface and Navigation

The main screens were tested to make sure that the user can move between the different parts of the application.

The following screens and navigation functions were tested:

- Home screen
- Pantry screen
- Suggested Recipes screen
- Recipe Details screen
- Settings screen
- Bottom navigation

The Pantry screen was tested with multiple items to confirm that the list remains usable when the number of ingredients increases.

The Settings screen was also opened to confirm that it loads correctly.

## 7. Overall Testing

The completed testing covered the main functions of Smart Pantry Manager, including pantry CRUD operations, input validation, pantry list scrolling, automatic item numbering, SQLite persistence, strict recipe matching, unit handling, recipe details and navigation.

The tests performed on the Android Emulator showed that the main application functions were working correctly and that pantry information was being stored and retrieved as expected.
