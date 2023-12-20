package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.CustomAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.Category;

public class AddCategoryFragment extends Fragment {

    private GridView gridIcons;
    private View viewPre = null;
    private final int selectedRow = -1;
    private final ArrayList<Integer> icons = new ArrayList<Integer>();
    private CustomAdapter adapter;
    private MyDatabase myDatabase;
    private  Category category;
    private int i;

    public AddCategoryFragment(Category category){
        this.category = category;
    }

    public AddCategoryFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.add_category_fragment, container, false);
       //Add icon
        addIcons();

        //Innit database
        myDatabase = new MyDatabase(getContext());



        /*------------Hook-------------*/
        gridIcons = fragment.findViewById(R.id.gridIcons);
        EditText edtNameCategory = fragment.findViewById(R.id.edtNameCategory);
        ImageView iconMain = fragment.findViewById(R.id.iconMain);
        AppCompatButton btnSave = fragment.findViewById(R.id.btnSave);
        AppCompatButton btnUpdate = fragment.findViewById(R.id.btnUpdate);
        AppCompatButton btnDelete = fragment.findViewById(R.id.btnDelete);




        if(category != null){
            iconMain.setImageResource((int)category.getIcon());
            edtNameCategory.setText(category.getContent());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Category newCategory = new Category();
                    String categoryName = edtNameCategory.getText().toString();
                    long icon = icons.get(i);
                    newCategory.setId(category.getId());
                    newCategory.setIcon(icon);
                    newCategory.setContent(categoryName);
                   int check = myDatabase.updateCategory(newCategory);

                    if(check > 0){
                        FragmentManager fragmentManager =((AppCompatActivity) getContext()).getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack();
                        }
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int check = myDatabase.deleteCategory(category.getId());
                    if(check > 0){
                        FragmentManager fragmentManager =((AppCompatActivity) getContext()).getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack();
                        }
                    }
                }
            });

            btnSave.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
        }
        else{
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long icon = icons.get(i);
                    Log.d("test2", "Clicked image: " + icon);
                    Log.d("test2", "--------: " );
                    String categoryName = edtNameCategory.getText().toString();
                    Category category = new Category(icon, categoryName);
                    long check = myDatabase.addCategory(category);
                    if(check > 0){
                        FragmentManager fragmentManager =((AppCompatActivity) getContext()).getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack();
                        }
                    }
                }
            });

            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }


        /*--------------------------*/
        adapter = new CustomAdapter(getContext(), R.layout.custom_grid_view, icons);
        gridIcons.setAdapter(adapter);
        gridIcons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*-------- Xu ly them background----------*/
                ImageView icon = view.findViewById(R.id.viewImageIcon);
                ImageView iconPre = view.findViewById(R.id.viewImageIcon);
                 i = position;

                //set icon to iconMain
                iconMain.setImageResource(icons.get(position));
                if (viewPre == view) {
                    icon.setBackground(getResources().getDrawable(R.drawable.radio_bg));
                    //iconMain.setImageDrawable(getResources().getDrawable(R.drawable.home));
                    iconMain.setImageResource(R.drawable.home);
                    viewPre = null;
                } else {
                    if (viewPre != null) {
                        iconPre = viewPre.findViewById(R.id.viewImageIcon);
                        iconPre.setBackground(getResources().getDrawable(R.drawable.radio_bg));
                    }
                    icon.setBackground(getResources().getDrawable(R.drawable.radio_bg_focus));
                    viewPre = view;
                }
            }
        });

        return  fragment;
    }

    private void addIcons(){
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
    }

}
