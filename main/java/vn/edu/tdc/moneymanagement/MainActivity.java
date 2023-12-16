package vn.edu.tdc.moneymanagement;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.model.SpinnerItemAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    private ArrayList<String> categories = new ArrayList<String>();
    private ArrayList<Integer> icons = new ArrayList<Integer>( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_spending_fragment);

        //R.layout.add_spending_fragment
        categories.add("Ăn uống");
        categories.add("Cafe");
        categories.add("Đi lại");
        categories.add("Du lịch");

        icons.add(R.drawable.healthy_eating);
        icons.add(R.drawable.coffee_cup);
        icons.add(R.drawable.road);
        icons.add(R.drawable.travel);

        Spinner spinner = findViewById(R.id.spinCategories);
        spinner.setOnItemSelectedListener(this);

        SpinnerItemAdapter spinnerItemAdapter = new SpinnerItemAdapter(this, categories, icons);
        spinner.setAdapter(spinnerItemAdapter);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int itemID  = item.getItemId();
//                if(itemID == R.id.home){
//                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                }
//                else  if(itemID == R.id.add){
//                    Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
//                }
//                else  if(itemID == R.id.history){
//                    Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//        recyclerView = findViewById(R.id.recyclerviewParent);
//        ArrayList<String> list = new ArrayList<>();
//        list.add("Hello");
//        list.add("Test");
//
//        listAdapter = new ListAdapter(this, list);
//        recyclerView.setAdapter(listAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, categories.get(i), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}