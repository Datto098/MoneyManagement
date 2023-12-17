package vn.edu.tdc.moneymanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import vn.edu.tdc.moneymanagement.model.ExpenseAdapter;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        List<String> fakedata = Arrays.asList("1000", "2000", "3000");

        RecyclerView expenseRecyclerView = findViewById(R.id.recyclerViewExpense);

        // Create and set up your custom adapter
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(fakedata);
        expenseRecyclerView.setAdapter(expenseAdapter);

        // Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        expenseRecyclerView.setLayoutManager(layoutManager);
    }
}