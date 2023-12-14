package vn.edu.tdc.moneymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.model.CustomAdapter;

public class AddCategoryActivity extends AppCompatActivity {

    private GridView gridIcons;
    private View viewPre = null;
    private int selectedRow = -1;
    private ArrayList<Integer> icons = new ArrayList<Integer>();
    private CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_layout);
        /*------------Hook-------------*/
        gridIcons = findViewById(R.id.gridIcons);
        EditText edtNameCategory = findViewById(R.id.edtNameCategory);
        ImageView iconMain = findViewById(R.id.iconMain);


        icons.add(R.drawable.buy);
        icons.add(R.drawable.coffee);
        icons.add(R.drawable.drink);
        icons.add(R.drawable.eat);
        icons.add(R.drawable.soda);
        icons.add(R.drawable.jewelry);
        icons.add(R.drawable.catering);
        icons.add(R.drawable.cycle);
        icons.add(R.drawable.friends);
        icons.add(R.drawable.clothes);
        icons.add(R.drawable.travel);
        icons.add(R.drawable.volleyball);
        icons.add(R.drawable.tourists);
        icons.add(R.drawable.accessories);
        icons.add(R.drawable.bus_stop);
        icons.add(R.drawable.internet);
        icons.add(R.drawable.medicine);
        icons.add(R.drawable.wifi);
        icons.add(R.drawable.wedding);
        icons.add(R.drawable.computer_game);
        icons.add(R.drawable.hairdresser);
        icons.add(R.drawable.cosmetics);
        icons.add(R.drawable.tuition);
        icons.add(R.drawable.bitcoin);
        icons.add(R.drawable.hospital);


        /*--------------------------*/
        adapter = new CustomAdapter(this, R.layout.custom_grid_view, icons);
        gridIcons.setAdapter(adapter);
        gridIcons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*-------- Xu ly them background----------*/
                ImageView icon = view.findViewById(R.id.viewImage);
                ImageView iconPre = view.findViewById(R.id.viewImage);


                //set icon to iconMain
                iconMain.setImageDrawable(icon.getDrawable());
                if (viewPre == view){
                    icon.setBackground(getResources().getDrawable(R.drawable.radio_bg));
                    iconMain.setImageDrawable(getResources().getDrawable(R.drawable.home));
                    viewPre = null;
                }else{
                    if (viewPre != null) {
                        iconPre = viewPre.findViewById(R.id.viewImage);
                        iconPre.setBackground(getResources().getDrawable(R.drawable.radio_bg));
                    }
                    icon.setBackground(getResources().getDrawable(R.drawable.radio_bg_focus));
                    viewPre = view;
                }

            }
        });
    }
}