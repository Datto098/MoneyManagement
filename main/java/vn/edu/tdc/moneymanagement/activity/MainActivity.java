package vn.edu.tdc.moneymanagement.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.ListAdapter;
import vn.edu.tdc.moneymanagement.fragment.AccountFragment;

public class MainActivity extends AppCompatActivity {
    public static String prevTitle = "";
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.account);
        prevTitle = "Tài khoản";


        AccountFragment fragment = new AccountFragment();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                getSupportActionBar().setTitle(prevTitle);
                fragmentManager.popBackStack();
            } else {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

