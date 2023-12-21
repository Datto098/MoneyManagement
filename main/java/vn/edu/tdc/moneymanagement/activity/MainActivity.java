package vn.edu.tdc.moneymanagement.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.ListAdapter;
import vn.edu.tdc.moneymanagement.fragment.AccountFragment;
import vn.edu.tdc.moneymanagement.fragment.AddFixedAccount;
import vn.edu.tdc.moneymanagement.fragment.AddSpendingFragment;
import vn.edu.tdc.moneymanagement.fragment.EnterMoneyFragment;
import vn.edu.tdc.moneymanagement.fragment.ExpensesFragment;
import vn.edu.tdc.moneymanagement.fragment.FixedAccountFragment;
import vn.edu.tdc.moneymanagement.fragment.TotalAmountFragment;

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
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        AccountFragment fragment = new AccountFragment();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }

    // Show option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                // Lấy ra thông tin về fragment hiện tại trước khi popBackStack
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container_view_tag);

                if (currentFragment != null) {
                    String currentTitle = "";

                    String className = currentFragment.getClass().getSimpleName();
                    if ("TotalAmountFragment".equals(className)) {
                        Log.d("test", currentFragment.getClass().getSimpleName() + "");
                        currentTitle = TotalAmountFragment.prevTitle;
                    } else if ("EnterMoneyFragment".equals(className)) {
                        Log.d("test", currentFragment.getClass().getSimpleName() + "");
                        currentTitle = EnterMoneyFragment.prevTitle;
                    } else if ("FixedAccountFragment".equals(className)) {
                        Log.d("test", currentFragment.getClass().getSimpleName() + "");
                        currentTitle = FixedAccountFragment.prevTitle;
                    } else if ("AddFixedAccount".equals(className)) {
                        Log.d("test", currentFragment.getClass().getSimpleName() + "");
                        currentTitle = AddFixedAccount.prevTitle;
                    } else if ("ExpensesFragment".equals(className)) {
                        Log.d("test", currentFragment.getClass().getSimpleName() + "");
                        currentTitle = ExpensesFragment.prevTitle;
                    } else if ("AddSpendingFragment".equals(className)) {
                        Log.d("test", currentFragment.getClass().getSimpleName() + "");
                        currentTitle = AddSpendingFragment.prevTitle;
                    }

                    getSupportActionBar().setTitle(currentTitle);
                }
                fragmentManager.popBackStack();
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        } else if (item.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

