package vn.edu.tdc.moneymanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.tdc.moneymanagement.fragment.ExpensesFragment;


public class TestExpensesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpensesFragment fragment = new ExpensesFragment();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }
}