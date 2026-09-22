package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    private Button btnNavHome;
    private Button btnNavPantry;
    private Button btnNavSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_settings);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // Connect navigation buttons
        btnNavHome = findViewById(R.id.btnNavHome);
        btnNavPantry = findViewById(R.id.btnNavPantry);
        btnNavSettings = findViewById(R.id.btnNavSettings);

        // Home navigation
        btnNavHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    SettingsActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
        });

        // Pantry navigation
        btnNavPantry.setOnClickListener(v -> {

            Intent intent = new Intent(
                    SettingsActivity.this,
                    PantryActivity.class
            );

            startActivity(intent);
        });

        // Already on Settings
        btnNavSettings.setOnClickListener(v -> {
            // Already on Settings screen
        });
    }
}

