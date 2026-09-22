package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private Button btnAddIngredient;

    // Bottom navigation buttons
    private Button btnNavHome;
    private Button btnNavPantry;
    private Button btnNavSettings;

    private DatabaseHelper databaseHelper;

    private List<PantryItem> pantryItems;
    private PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        // Connect bottom navigation buttons
        btnNavHome = findViewById(R.id.btnNavHome);
        btnNavPantry = findViewById(R.id.btnNavPantry);
        btnNavSettings = findViewById(R.id.btnNavSettings);

        databaseHelper = new DatabaseHelper(this);

        pantryItems = new ArrayList<>();

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        pantryAdapter = new PantryAdapter(
                pantryItems,
                new PantryAdapter.OnPantryItemActionListener() {

                    @Override
                    public void onEdit(PantryItem item) {

                        Intent intent = new Intent(
                                PantryActivity.this,
                                AddEditPantryActivity.class
                        );

                        intent.putExtra("EDIT_ID", item.getId());
                        intent.putExtra("EDIT_NAME", item.getName());
                        intent.putExtra("EDIT_QUANTITY", item.getQuantity());
                        intent.putExtra("EDIT_UNIT", item.getUnit());
                        intent.putExtra("EDIT_EXPIRY", item.getExpiryDate());

                        startActivity(intent);
                    }

                    @Override
                    public void onDelete(PantryItem item) {

                        databaseHelper.deletePantryItem(
                                item.getId()
                        );

                        loadPantryItems();
                    }
                }
        );

        recyclerPantry.setAdapter(pantryAdapter);

        // Add new ingredient
        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PantryActivity.this,
                    AddEditPantryActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Home
        btnNavHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PantryActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
        });

        // Bottom navigation - Pantry
        btnNavPantry.setOnClickListener(v -> {
            // Already on the Pantry screen
        });

        // Bottom navigation - Settings
        btnNavSettings.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PantryActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        pantryItems.clear();

        Cursor cursor =
                databaseHelper.getAllPantryItems();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("quantity")
            );

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow("unit")
            );

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("expiry_date")
            );

            PantryItem item = new PantryItem(
                    id,
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            pantryItems.add(item);
        }

        cursor.close();

        pantryAdapter.notifyDataSetChanged();
    }
}

