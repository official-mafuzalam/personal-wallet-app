package com.octosync.personalwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvTotalBal, tvExpense, tvAddExpense, tvExpanseAll, tvIncome, tvAddIncome, tvIncomeAll;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTotalBal = findViewById(R.id.tvTotalBal);
        tvExpense = findViewById(R.id.tvExpense);
        tvAddExpense = findViewById(R.id.tvAddExpense);
        tvExpanseAll = findViewById(R.id.tvExpanseAll);
        tvIncome = findViewById(R.id.tvIncome);
        tvAddIncome = findViewById(R.id.tvAddIncome);
        tvIncomeAll = findViewById(R.id.tvIncomeAll);

        dbHelper = new DBHelper(MainActivity.this);

        updateUi();

        tvAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData.INCOME = false;
                startActivity(new Intent(MainActivity.this, AddData.class));
            }
        });

        tvExpanseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.INCOME = false;
                startActivity(new Intent(MainActivity.this, ShowData.class));
            }
        });

        tvAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData.INCOME = true;
                startActivity(new Intent(MainActivity.this, AddData.class));
            }
        });

        tvIncomeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.INCOME = true;
                startActivity(new Intent(MainActivity.this, ShowData.class));
            }
        });


    }

    public void updateUi() {
        tvExpense.setText("BDT: " + dbHelper.totalExpense() + " TK");
        tvIncome.setText("BDT: " + dbHelper.totalIncome() + " TK");
        tvTotalBal.setText("BDT: " + (dbHelper.totalIncome() - dbHelper.totalExpense()) + " TK");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUi();
    }
}