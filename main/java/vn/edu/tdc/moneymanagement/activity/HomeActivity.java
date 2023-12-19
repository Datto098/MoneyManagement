package vn.edu.tdc.moneymanagement.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.TestEnterMoneyActivity;
import vn.edu.tdc.moneymanagement.fragment.HistoryFragment;
import vn.edu.tdc.moneymanagement.fragment.HomeFragment;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_management_drawer_layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*--------------------Hooks------------------*/
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navi);
        drawerLayout = findViewById(R.id.drawerLayout);

        /*--------------------toolbar------------------*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        /*--------------------navi drawer menu------------------*/
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        /*--------------------Dán home fragment------------------*/

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutHome, fragment);
        transaction.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.home) {
                    Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle(R.string.home);
                    HomeFragment fragment = new HomeFragment();
                    getFragment(fragment);

                } else if (itemID == R.id.history) {
                    Toast.makeText(HomeActivity.this, "History", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle(R.string.lich_su);
                    HistoryFragment fragment = new HistoryFragment();
                    getFragment(fragment);
                } else if (itemID == R.id.account) {
                    Toast.makeText(HomeActivity.this, "Account", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }


    //Xu ly nut drawerToggle


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_home) {

        } else if (menuItem.getItemId() == R.id.nav_account) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.nav_totalPrice) {
            Intent intent = new Intent(this, TestEnterMoneyActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, TestEnterMoneyActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Ham dua fragment vao
    public void getFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutHome, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
