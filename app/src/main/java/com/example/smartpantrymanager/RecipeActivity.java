package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerRecipes;
    private TextView txtNoRecipes;

    // Bottom navigation buttons
    private Button btnNavHome;
    private Button btnNavPantry;
    private Button btnNavSettings;

    private DatabaseHelper databaseHelper;

    private List<Recipe> recipeList;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        // Connect bottom navigation buttons
        btnNavHome = findViewById(R.id.btnNavHome);
        btnNavPantry = findViewById(R.id.btnNavPantry);
        btnNavSettings = findViewById(R.id.btnNavSettings);

        databaseHelper = new DatabaseHelper(this);

        recipeList = new ArrayList<>();

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recipeAdapter = new RecipeAdapter(
                this,
                recipeList
        );

        recyclerRecipes.setAdapter(recipeAdapter);

        // Bottom navigation - Home
        btnNavHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecipeActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Pantry
        btnNavPantry.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecipeActivity.this,
                    PantryActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Settings
        btnNavSettings.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RecipeActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null && recipeList != null) {
            loadSuggestedRecipes();
        }
    }

    private void loadSuggestedRecipes() {

        recipeList.clear();

        Cursor recipeCursor =
                databaseHelper.getAllRecipes();

        while (recipeCursor.moveToNext()) {

            int recipeId = recipeCursor.getInt(
                    recipeCursor.getColumnIndexOrThrow("id")
            );

            String recipeName = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("name")
            );

            String steps = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("steps")
            );

            Recipe recipe = new Recipe(
                    recipeId,
                    recipeName,
                    steps
            );

            Cursor ingredientCursor =
                    databaseHelper.getRecipeIngredients(recipeId);

            while (ingredientCursor.moveToNext()) {

                String ingredientName =
                        ingredientCursor.getString(
                                ingredientCursor.getColumnIndexOrThrow(
                                        "ingredient_name"
                                )
                        );

                double requiredQuantity =
                        ingredientCursor.getDouble(
                                ingredientCursor.getColumnIndexOrThrow(
                                        "required_quantity"
                                )
                        );

                String unit =
                        ingredientCursor.getString(
                                ingredientCursor.getColumnIndexOrThrow(
                                        "unit"
                                )
                        );

                recipe.addIngredient(
                        new RecipeIngredient(
                                ingredientName,
                                requiredQuantity,
                                unit
                        )
                );
            }

            ingredientCursor.close();

            /*
             * A recipe is suggested ONLY when every ingredient
             * is available in the pantry in a sufficient quantity.
             */
            if (canMakeRecipe(recipe)) {
                recipeList.add(recipe);
            }
        }

        recipeCursor.close();

        recipeAdapter.notifyDataSetChanged();

        if (recipeList.isEmpty()) {

            txtNoRecipes.setVisibility(View.VISIBLE);
            recyclerRecipes.setVisibility(View.GONE);

        } else {

            txtNoRecipes.setVisibility(View.GONE);
            recyclerRecipes.setVisibility(View.VISIBLE);
        }
    }

    private boolean canMakeRecipe(Recipe recipe) {

        for (RecipeIngredient requiredIngredient :
                recipe.getIngredients()) {

            if (!hasEnoughIngredient(requiredIngredient)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasEnoughIngredient(
            RecipeIngredient requiredIngredient
    ) {

        Cursor pantryCursor =
                databaseHelper.getAllPantryItems();

        while (pantryCursor.moveToNext()) {

            String pantryName =
                    pantryCursor.getString(
                            pantryCursor.getColumnIndexOrThrow("name")
                    );

            double pantryQuantity =
                    pantryCursor.getDouble(
                            pantryCursor.getColumnIndexOrThrow("quantity")
                    );

            String pantryUnit =
                    pantryCursor.getString(
                            pantryCursor.getColumnIndexOrThrow("unit")
                    );

            boolean sameName =
                    normaliseIngredientName(pantryName)
                            .equals(
                                    normaliseIngredientName(
                                            requiredIngredient.getName()
                                    )
                            );

            double convertedQuantity =
                    convertToRequiredUnit(
                            pantryQuantity,
                            pantryUnit,
                            requiredIngredient.getUnit()
                    );

            boolean enoughQuantity =
                    convertedQuantity >=
                            requiredIngredient.getRequiredQuantity();

            if (sameName && enoughQuantity) {

                pantryCursor.close();
                return true;
            }
        }

        pantryCursor.close();

        return false;
    }

    private String normaliseIngredientName(String name) {

        String normalised =
                name.trim()
                        .toLowerCase(Locale.ROOT);

        if (normalised.endsWith("ies")) {

            normalised =
                    normalised.substring(
                            0,
                            normalised.length() - 3
                    ) + "y";

        } else if (normalised.endsWith("oes")) {

            normalised =
                    normalised.substring(
                            0,
                            normalised.length() - 2
                    );

        } else if (normalised.endsWith("s")
                && !normalised.endsWith("ss")) {

            normalised =
                    normalised.substring(
                            0,
                            normalised.length() - 1
                    );
        }

        return normalised;
    }

    private double convertToRequiredUnit(
            double quantity,
            String pantryUnit,
            String requiredUnit
    ) {

        String pantry =
                normaliseUnit(pantryUnit);

        String required =
                normaliseUnit(requiredUnit);

        if (pantry.equals(required)) {
            return quantity;
        }

        if (pantry.equals("gram")
                && required.equals("kilogram")) {

            return quantity / 1000.0;
        }

        if (pantry.equals("kilogram")
                && required.equals("gram")) {

            return quantity * 1000.0;
        }

        if (pantry.equals("milliliter")
                && required.equals("liter")) {

            return quantity / 1000.0;
        }

        if (pantry.equals("liter")
                && required.equals("milliliter")) {

            return quantity * 1000.0;
        }

        if (pantry.equals("teaspoon")
                && required.equals("tablespoon")) {

            return quantity / 3.0;
        }

        if (pantry.equals("tablespoon")
                && required.equals("teaspoon")) {

            return quantity * 3.0;
        }

        if (pantry.equals("piece")
                && required.equals("piece")) {

            return quantity;
        }

        return 0;
    }

    private String normaliseUnit(String unit) {

        String normalised =
                unit.trim()
                        .toLowerCase(Locale.ROOT);

        if (normalised.equals("grams")
                || normalised.equals("gram")) {

            return "gram";
        }

        if (normalised.equals("kilograms")
                || normalised.equals("kilogram")
                || normalised.equals("kg")) {

            return "kilogram";
        }

        if (normalised.equals("millilitres")
                || normalised.equals("milliliters")
                || normalised.equals("millilitre")
                || normalised.equals("milliliter")
                || normalised.equals("ml")) {

            return "milliliter";
        }

        if (normalised.equals("litres")
                || normalised.equals("liters")
                || normalised.equals("litre")
                || normalised.equals("liter")
                || normalised.equals("l")) {

            return "liter";
        }

        if (normalised.equals("teaspoons")
                || normalised.equals("teaspoon")
                || normalised.equals("tsp")) {

            return "teaspoon";
        }

        if (normalised.equals("tablespoons")
                || normalised.equals("tablespoon")
                || normalised.equals("tbsp")) {

            return "tablespoon";
        }

        if (normalised.equals("pieces")
                || normalised.equals("piece")
                || normalised.equals("pcs")) {

            return "piece";
        }

        return normalised;
    }
}