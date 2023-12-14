package vn.edu.tdc.moneymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import vn.edu.tdc.moneymanagement.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_management_drawer_layout);
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

    }



    //Xu ly nut drawerToggle


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_home){

        }
        else if (menuItem.getItemId() == R.id.nav_account)
        {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.nav_totalPrice) {
            Intent intent = new Intent(this, TestEnterMoneyActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, TestEnterMoneyActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}
