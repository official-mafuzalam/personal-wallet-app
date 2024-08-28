package com.octosync.personalwallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddData extends AppCompatActivity {

    TextView tvTitle;
    EditText edCategory, edAmount;
    Button btnSave;
    public static boolean INCOME = true;

    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTitle = findViewById(R.id.tvTitle);
        edCategory = findViewById(R.id.edCategory);
        edAmount = findViewById(R.id.edAmount);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DBHelper(AddData.this);

        if (INCOME == true) {
            tvTitle.setText("Add Income");
        } else {
            tvTitle.setText("Add Expense");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String cat = edCategory.getText().toString();
                float amount = Float.parseFloat(edAmount.getText().toString());

                if (INCOME == true) {
                    dbHelper.addIncome(cat, amount);
                    tvTitle.setText("Income added successfully.");
                } else {
                    dbHelper.addExpense(cat, amount);
                    tvTitle.setText("Expense added successfully.");
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}