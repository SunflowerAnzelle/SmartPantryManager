package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditPantryActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private Spinner spinnerUnit;
    private EditText etExpiryDate;
    private Button btnSaveIngredient;
    private Button btnCancel;
    private TextView tvFormTitle;

    private DatabaseHelper databaseHelper;

    // Stores the ID of the ingredient being edited.
    // -1 means we are adding a new ingredient.
    private int editId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pantry);

        tvFormTitle = findViewById(R.id.tvFormTitle);
        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        btnCancel = findViewById(R.id.btnCancel);

        databaseHelper = new DatabaseHelper(this);

        String[] units = {
                "pieces",
                "grams",
                "kilograms",
                "millilitres",
                "litres",
                "teaspoons"
        };

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );

        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnit.setAdapter(unitAdapter);

        // Check whether an existing ingredient was selected for editing.
        if (getIntent().hasExtra("EDIT_ID")) {

            editId = getIntent().getIntExtra("EDIT_ID", -1);

            String editName = getIntent().getStringExtra("EDIT_NAME");
            double editQuantity = getIntent().getDoubleExtra(
                    "EDIT_QUANTITY",
                    0
            );
            String editUnit = getIntent().getStringExtra("EDIT_UNIT");
            String editExpiry = getIntent().getStringExtra("EDIT_EXPIRY");

            tvFormTitle.setText("Edit Ingredient");
            btnSaveIngredient.setText("Update Ingredient");

            etIngredientName.setText(editName);
            etQuantity.setText(String.valueOf(editQuantity));
            etExpiryDate.setText(editExpiry);

            // Select the existing unit in the Spinner.
            for (int i = 0; i < units.length; i++) {

                if (units[i].equals(editUnit)) {
                    spinnerUnit.setSelection(i);
                    break;
                }
            }
        }

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveIngredient() {

        String name = etIngredientName.getText().toString().trim();
        String quantityText = etQuantity.getText().toString().trim();
        String unit = spinnerUnit.getSelectedItem().toString();
        String expiryDate = etExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {

            etIngredientName.setError(
                    "Please enter an ingredient name"
            );

            etIngredientName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {

            etQuantity.setError(
                    "Please enter a quantity"
            );

            etQuantity.requestFocus();
            return;
        }

        double quantity;

        try {

            quantity = Double.parseDouble(quantityText);

        } catch (NumberFormatException e) {

            etQuantity.setError(
                    "Please enter a valid quantity"
            );

            etQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {

            etQuantity.setError(
                    "Quantity must be greater than 0"
            );

            etQuantity.requestFocus();
            return;
        }

        PantryItem item = new PantryItem(
                name,
                quantity,
                unit,
                expiryDate
        );

        // If editId is -1, create a new ingredient.
        if (editId == -1) {

            long result = databaseHelper.addPantryItem(item);

            if (result != -1) {

                Toast.makeText(
                        this,
                        "Ingredient added successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to add ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            // Otherwise, update the existing ingredient.
            item.setId(editId);

            boolean updated = databaseHelper.updatePantryItem(item);

            if (updated) {

                Toast.makeText(
                        this,
                        "Ingredient updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}
