package com.example.smartpantrymanager;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Pantry items table
        db.execSQL(
                "CREATE TABLE pantry_items (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "expiry_date TEXT)"
        );

        // Recipes table
        db.execSQL(
                "CREATE TABLE recipes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "steps TEXT NOT NULL)"
        );

        // Ingredients required by each recipe
        db.execSQL(
                "CREATE TABLE recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "required_quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) REFERENCES recipes(id) ON DELETE CASCADE)"
        );

        // Add the default recipes when the database is created
        insertDefaultRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {

            // The recipe tables already exist from version 1.
            // We only add the default recipes.
            insertDefaultRecipes(db);
        }
    }

    // ============================================================
    // PANTRY METHODS
    // ============================================================

    // Add a pantry item
    public long addPantryItem(PantryItem item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("quantity", item.getQuantity());
        values.put("unit", item.getUnit());
        values.put("expiry_date", item.getExpiryDate());

        long id = db.insert("pantry_items", null, values);

        db.close();

        return id;
    }

    // Get all pantry items
    public Cursor getAllPantryItems() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM pantry_items ORDER BY name ASC",
                null
        );
    }

    // Update a pantry item
    public boolean updatePantryItem(PantryItem item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("quantity", item.getQuantity());
        values.put("unit", item.getUnit());
        values.put("expiry_date", item.getExpiryDate());

        int rowsAffected = db.update(
                "pantry_items",
                values,
                "id = ?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();

        return rowsAffected > 0;
    }

    // Delete a pantry item
    public boolean deletePantryItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int rowsDeleted = db.delete(
                "pantry_items",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return rowsDeleted > 0;
    }


    // ============================================================
    // DEFAULT RECIPES
    // ============================================================

    private void insertDefaultRecipes(SQLiteDatabase db) {

        // --------------------------------------------------------
        // 1. Tomato Omelette
        // --------------------------------------------------------

        long recipeId = insertRecipe(
                db,
                "Tomato Omelette",
                "1. Beat the eggs in a bowl.\n" +
                        "2. Chop the tomatoes and onion.\n" +
                        "3. Heat a little oil in a pan and cook the onion.\n" +
                        "4. Add the tomatoes and cook for a few minutes.\n" +
                        "5. Add the eggs, season with salt and black pepper, and cook until set."
        );

        insertRecipeIngredient(db, recipeId, "eggs", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "tomatoes", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 2. Cheese Sandwich
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Cheese Sandwich",
                "1. Place cheese between two slices of bread.\n" +
                        "2. Toast the sandwich until the bread is golden and the cheese has melted.\n" +
                        "3. Serve warm."
        );

        insertRecipeIngredient(db, recipeId, "bread", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "cheese", 2, "pieces");


        // --------------------------------------------------------
        // 3. Chicken Pasta
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Chicken Pasta",
                "1. Cook the pasta according to the package instructions.\n" +
                        "2. Chop the chicken and onion.\n" +
                        "3. Heat oil and cook the onion.\n" +
                        "4. Add the chicken and cook thoroughly.\n" +
                        "5. Season with salt, black pepper and mixed herbs.\n" +
                        "6. Add the cooked pasta and mix well."
        );

        insertRecipeIngredient(db, recipeId, "pasta", 200, "grams");
        insertRecipeIngredient(db, recipeId, "chicken", 250, "grams");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "mixed herbs", 0.5, "teaspoons");


        // --------------------------------------------------------
        // 4. Vegetable Rice
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Vegetable Rice",
                "1. Cook the rice.\n" +
                        "2. Chop the onion and carrots.\n" +
                        "3. Heat oil and cook the onion.\n" +
                        "4. Add the carrots and peas.\n" +
                        "5. Add the cooked rice.\n" +
                        "6. Season with salt, black pepper and a little mixed herbs."
        );

        insertRecipeIngredient(db, recipeId, "rice", 200, "grams");
        insertRecipeIngredient(db, recipeId, "carrots", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "peas", 100, "grams");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 5. Tomato Pasta
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Tomato Pasta",
                "1. Cook the pasta.\n" +
                        "2. Chop the tomatoes and onion.\n" +
                        "3. Heat oil and cook the onion.\n" +
                        "4. Add the tomatoes and cook until soft.\n" +
                        "5. Season with salt, black pepper and mixed herbs.\n" +
                        "6. Add the cooked pasta and mix well."
        );

        insertRecipeIngredient(db, recipeId, "pasta", 200, "grams");
        insertRecipeIngredient(db, recipeId, "tomatoes", 3, "pieces");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "mixed herbs", 0.5, "teaspoons");


        // --------------------------------------------------------
        // 6. Chicken Sandwich
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Chicken Sandwich",
                "1. Cook the chicken thoroughly with onion.\n" +
                        "2. Season with salt and black pepper.\n" +
                        "3. Place the chicken between two slices of bread.\n" +
                        "4. Add cheese and toast if desired."
        );

        insertRecipeIngredient(db, recipeId, "bread", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "chicken", 150, "grams");
        insertRecipeIngredient(db, recipeId, "cheese", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "onion", 0.5, "pieces");
        insertRecipeIngredient(db, recipeId, "salt", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 7. Scrambled Eggs
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Scrambled Eggs",
                "1. Crack the eggs into a bowl and beat them.\n" +
                        "2. Heat a little oil in a pan.\n" +
                        "3. Add the eggs and stir gently.\n" +
                        "4. Season with salt and black pepper.\n" +
                        "5. Cook until the eggs are fully set."
        );

        insertRecipeIngredient(db, recipeId, "eggs", 3, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 8. Rice and Chicken
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Rice and Chicken",
                "1. Cook the rice.\n" +
                        "2. Chop the chicken and onion.\n" +
                        "3. Heat oil and cook the onion.\n" +
                        "4. Add the chicken and cook thoroughly.\n" +
                        "5. Season with salt, black pepper and curry powder.\n" +
                        "6. Serve the chicken with the cooked rice."
        );

        insertRecipeIngredient(db, recipeId, "rice", 200, "grams");
        insertRecipeIngredient(db, recipeId, "chicken", 200, "grams");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "curry powder", 0.5, "teaspoons");


        // --------------------------------------------------------
        // 9. Toasted Tomato
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Toasted Tomato",
                "1. Toast the bread until lightly golden.\n" +
                        "2. Slice the tomatoes.\n" +
                        "3. Place the tomatoes on the toast.\n" +
                        "4. Season with a pinch of salt and black pepper.\n" +
                        "5. Serve immediately."
        );

        insertRecipeIngredient(db, recipeId, "bread", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "tomatoes", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "salt", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 10. Egg Sandwich
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Egg Sandwich",
                "1. Cook the eggs.\n" +
                        "2. Season with salt and black pepper.\n" +
                        "3. Place the eggs between two slices of bread.\n" +
                        "4. Add cheese and serve."
        );

        insertRecipeIngredient(db, recipeId, "eggs", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "bread", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "cheese", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "salt", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 11. Chicken Rice Bowl
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Chicken Rice Bowl",
                "1. Cook the rice.\n" +
                        "2. Chop the chicken, onion and carrots.\n" +
                        "3. Cook the onion in a little oil.\n" +
                        "4. Add the chicken and cook thoroughly.\n" +
                        "5. Add the carrots and cook until tender.\n" +
                        "6. Season with salt, black pepper and mixed herbs.\n" +
                        "7. Serve over the cooked rice."
        );

        insertRecipeIngredient(db, recipeId, "rice", 200, "grams");
        insertRecipeIngredient(db, recipeId, "chicken", 200, "grams");
        insertRecipeIngredient(db, recipeId, "carrots", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "mixed herbs", 0.5, "teaspoons");


        // --------------------------------------------------------
        // 12. Chicken and Vegetables
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Chicken and Vegetables",
                "1. Chop the chicken, onion and vegetables.\n" +
                        "2. Heat oil and cook the onion.\n" +
                        "3. Add the chicken and cook thoroughly.\n" +
                        "4. Add the carrots and peas.\n" +
                        "5. Season with salt, black pepper and mixed herbs.\n" +
                        "6. Cook until the vegetables are tender."
        );

        insertRecipeIngredient(db, recipeId, "chicken", 250, "grams");
        insertRecipeIngredient(db, recipeId, "carrots", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "peas", 100, "grams");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "mixed herbs", 0.5, "teaspoons");


        // --------------------------------------------------------
        // 13. Simple Vegetable Pasta
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Simple Vegetable Pasta",
                "1. Cook the pasta.\n" +
                        "2. Chop the onion and carrots.\n" +
                        "3. Heat oil and cook the onion.\n" +
                        "4. Add the carrots and peas.\n" +
                        "5. Add the cooked pasta.\n" +
                        "6. Season with salt, black pepper and mixed herbs."
        );

        insertRecipeIngredient(db, recipeId, "pasta", 200, "grams");
        insertRecipeIngredient(db, recipeId, "carrots", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "peas", 100, "grams");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "mixed herbs", 0.5, "teaspoons");


        // --------------------------------------------------------
        // 14. Easy Pancakes
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Easy Pancakes",
                "1. Mix the flour, sugar and salt in a bowl.\n" +
                        "2. Add the eggs and milk.\n" +
                        "3. Mix until you have a smooth batter.\n" +
                        "4. Heat a lightly oiled pan.\n" +
                        "5. Pour in some batter and cook both sides until golden.\n" +
                        "6. Repeat until all the batter is used."
        );

        insertRecipeIngredient(db, recipeId, "flour", 200, "grams");
        insertRecipeIngredient(db, recipeId, "eggs", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "milk", 250, "millilitres");
        insertRecipeIngredient(db, recipeId, "sugar", 2, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "cooking oil", 2, "teaspoons");


        // --------------------------------------------------------
        // 15. Fried Rice
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Fried Rice",
                "1. Cook the rice and allow it to cool.\n" +
                        "2. Chop the onion and carrots.\n" +
                        "3. Heat oil in a pan and cook the onion.\n" +
                        "4. Add the carrots and peas.\n" +
                        "5. Add the eggs and scramble them.\n" +
                        "6. Add the cooked rice and mix everything together.\n" +
                        "7. Season with salt and black pepper and fry for a few minutes."
        );

        insertRecipeIngredient(db, recipeId, "rice", 250, "grams");
        insertRecipeIngredient(db, recipeId, "eggs", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "carrots", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "peas", 100, "grams");
        insertRecipeIngredient(db, recipeId, "cooking oil", 2, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 16. Macaroni and Mince
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Macaroni and Mince",
                "1. Cook the macaroni and drain it.\n" +
                        "2. Chop the onion and tomatoes.\n" +
                        "3. Heat oil and cook the onion.\n" +
                        "4. Add the mince and cook thoroughly.\n" +
                        "5. Add the tomatoes and cook until soft.\n" +
                        "6. Season with salt, black pepper and mixed herbs.\n" +
                        "7. Add the cooked macaroni and mix well."
        );

        insertRecipeIngredient(db, recipeId, "macaroni", 250, "grams");
        insertRecipeIngredient(db, recipeId, "mince", 250, "grams");
        insertRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "tomatoes", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "cooking oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.5, "teaspoons");
        insertRecipeIngredient(db, recipeId, "black pepper", 0.25, "teaspoons");
        insertRecipeIngredient(db, recipeId, "mixed herbs", 0.5, "teaspoons");


        // --------------------------------------------------------
        // 17. Tomato and Cucumber Salad
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Tomato and Cucumber Salad",
                "1. Chop the tomatoes and cucumber.\n" +
                        "2. Place them in a bowl.\n" +
                        "3. Add olive oil and a pinch of salt.\n" +
                        "4. Mix gently and serve."
        );

        insertRecipeIngredient(db, recipeId, "tomatoes", 2, "pieces");
        insertRecipeIngredient(db, recipeId, "cucumber", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "olive oil", 1, "teaspoons");
        insertRecipeIngredient(db, recipeId, "salt", 0.25, "teaspoons");


        // --------------------------------------------------------
        // 18. Simple Greek-inspired Salad
        // --------------------------------------------------------

        recipeId = insertRecipe(
                db,
                "Simple Greek-inspired Salad",
                "1. Chop the cucumber and tomato.\n" +
                        "2. Place them in a bowl.\n" +
                        "3. Add the crumbled feta cheese.\n" +
                        "4. Mix gently and serve."
        );

        insertRecipeIngredient(db, recipeId, "cucumber", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "tomato", 1, "pieces");
        insertRecipeIngredient(db, recipeId, "feta cheese", 50, "grams");
    }


    // ============================================================
    // RECIPE DATABASE METHODS
    // ============================================================

    private long insertRecipe(
            SQLiteDatabase db,
            String name,
            String steps
    ) {

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("steps", steps);

        return db.insert(
                "recipes",
                null,
                values
        );
    }


    private long insertRecipeIngredient(
        SQLiteDatabase db,
        long recipeId,
        String ingredientName,
        double quantity,
        String unit
) {

    ContentValues values = new ContentValues();

    values.put("recipe_id", recipeId);
    values.put("ingredient_name", ingredientName);
    values.put("required_quantity", quantity);
    values.put("unit", unit);

    return db.insert(
            "recipe_ingredients",
            null,
            values
    );
}


// ============================================================
// RECIPE RETRIEVAL METHODS
// ============================================================

// Get all recipes
public Cursor getAllRecipes() {

    SQLiteDatabase db = this.getReadableDatabase();

    return db.rawQuery(
            "SELECT id, name, steps FROM recipes ORDER BY name ASC",
            null
    );
}


// Get ingredients for a specific recipe
public Cursor getRecipeIngredients(int recipeId) {

    SQLiteDatabase db = this.getReadableDatabase();

    return db.rawQuery(
            "SELECT ingredient_name, required_quantity, unit " +
                    "FROM recipe_ingredients " +
                    "WHERE recipe_id = ? " +
                    "ORDER BY id ASC",
            new String[]{String.valueOf(recipeId)}
    );
}

}
