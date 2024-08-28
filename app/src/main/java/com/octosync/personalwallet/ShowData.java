package com.octosync.personalwallet;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ShowData extends AppCompatActivity {

    TextView tvTitle;
    ListView listView;
    DBHelper dbHelper;
    public static boolean INCOME = true;
    ArrayList<HashMap<String, String>> arrayList;
    HashMap<String, String> hashMap;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTitle = findViewById(R.id.tvTitle);
        listView = findViewById(R.id.listView);

        dbHelper = new DBHelper(this);


        loadData();
    }

    public void loadData() {
        if (INCOME == true) {
            tvTitle.setText("Income");
        } else {
            tvTitle.setText("Expense");
        }

        Cursor cursor;
        if (INCOME == true) {
            cursor = dbHelper.showData(1);
        } else {
            cursor = dbHelper.showData(0);
        }

        if (cursor != null && cursor.getCount() > 0) {

            arrayList = new ArrayList<>();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String category = cursor.getString(1);
                float amount = cursor.getFloat(2);
                double time = cursor.getDouble(3);

                hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("category", category);
                hashMap.put("amount", String.valueOf(amount));
                hashMap.put("time", String.valueOf(time));
                arrayList.add(hashMap);
            }

            listView.setAdapter(new MyAdapter());
        } else {
            tvTitle.setText("No Data Found");
        }
    }

    //-------------------------------------

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_item, null);

            TextView tvId = view.findViewById(R.id.tvId);
            TextView tvCat = view.findViewById(R.id.tvCat);
            TextView tvAmount = view.findViewById(R.id.tvAmount);
            TextView tvTime = view.findViewById(R.id.tvTime);
            TextView tvDelete = view.findViewById(R.id.tvDelete);

            double time = Double.parseDouble(hashMap.get("time"));
            // Format the time and date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String formattedDateTime = sdf.format(new Date((long) time));

            hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            tvId.setText(id);
            tvCat.setText(hashMap.get("category"));
            tvAmount.setText(hashMap.get("amount"));
            tvTime.setText(formattedDateTime);

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (INCOME == true) {
                        dbHelper.deleteIncome(id);
                    } else {
                        dbHelper.deleteExpense(id);
                    }

                    loadData();
                }
            });

            return view;
        }
    }

}