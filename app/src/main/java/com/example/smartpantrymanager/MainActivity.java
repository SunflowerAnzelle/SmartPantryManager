package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnOpenPantry;
    private Button btnOpenRecipes;

    private Button btnNavHome;
    private Button btnNavPantry;
    private Button btnNavSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Connect the main screen buttons
        btnOpenPantry = findViewById(R.id.btnOpenPantry);
        btnOpenRecipes = findViewById(R.id.btnOpenRecipes);

        // Connect the bottom navigation buttons
        btnNavHome = findViewById(R.id.btnNavHome);
        btnNavPantry = findViewById(R.id.btnNavPantry);
        btnNavSettings = findViewById(R.id.btnNavSettings);

        // Add a new pantry item
        btnOpenPantry.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditPantryActivity.class
            );

            startActivity(intent);
        });

        // Open Suggested Recipes
        btnOpenRecipes.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    RecipeActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Home
        btnNavHome.setOnClickListener(v -> {
            // Already on the Home screen
        });

        // Bottom navigation - Pantry
        btnNavPantry.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    PantryActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Settings
        btnNavSettings.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });
    }
}

