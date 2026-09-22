package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtIngredients;
    private TextView txtSteps;

    // Bottom navigation buttons
    private Button btnNavHome;
    private Button btnNavPantry;
    private Button btnNavSettings;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        // Connect the screen elements
        txtRecipeName = findViewById(R.id.txtDetailRecipeName);
        txtIngredients = findViewById(R.id.txtDetailIngredients);
        txtSteps = findViewById(R.id.txtDetailSteps);

        // Connect bottom navigation buttons
        btnNavHome = findViewById(R.id.btnNavHome);
        btnNavPantry = findViewById(R.id.btnNavPantry);
        btnNavSettings = findViewById(R.id.btnNavSettings);

        // Connect to the database
        databaseHelper = new DatabaseHelper(this);

        // Get the recipe ID passed from RecipeAdapter
        int recipeId = getIntent().getIntExtra("recipeId", -1);

        // Load the recipe
        if (recipeId != -1) {
            loadRecipe(recipeId);
        }

        // Bottom navigation - Home
        btnNavHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecipeDetailActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Pantry
        btnNavPantry.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecipeDetailActivity.this,
                    PantryActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Settings
        btnNavSettings.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecipeDetailActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });
    }

    private void loadRecipe(int recipeId) {

        // Get the recipe name and method
        Cursor recipeCursor = databaseHelper.getReadableDatabase().rawQuery(
                "SELECT name, steps FROM recipes WHERE id = ?",
                new String[]{String.valueOf(recipeId)}
        );

        if (recipeCursor.moveToFirst()) {

            String recipeName = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("name")
            );

            String steps = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("steps")
            );

            txtRecipeName.setText(recipeName);
            txtSteps.setText(steps);
        }

        recipeCursor.close();

        // Get the recipe ingredients
        Cursor ingredientCursor =
                databaseHelper.getRecipeIngredients(recipeId);

        StringBuilder ingredientsText = new StringBuilder();

        while (ingredientCursor.moveToNext()) {

            String ingredientName = ingredientCursor.getString(
                    ingredientCursor.getColumnIndexOrThrow("ingredient_name")
            );

            double quantity = ingredientCursor.getDouble(
                    ingredientCursor.getColumnIndexOrThrow("required_quantity")
            );

            String unit = ingredientCursor.getString(
                    ingredientCursor.getColumnIndexOrThrow("unit")
            );

            ingredientsText.append("• ")
                    .append(quantity)
                    .append(" ")
                    .append(unit)
                    .append(" ")
                    .append(ingredientName)
                    .append("\n");
        }

        ingredientCursor.close();

        txtIngredients.setText(ingredientsText.toString());
    }
}