package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.CategoryAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.Category;

public class CategoryListFragment extends Fragment {

    private MyDatabase myDatabase;
    private ArrayList<Category> categories;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.category_list_fragment, container, false);

        //innit database
        myDatabase = new MyDatabase(getContext());

        categories = new ArrayList<>();

        RecyclerView listCategory = fragment.findViewById(R.id.listCategory);
        AppCompatButton btnAdd = fragment.findViewById(R.id.btnAdd);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryFragment fragment = new AddCategoryFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment);
                transaction.addToBackStack("fragment_spending_2");
                transaction.commit();
            }
        });

        categories = myDatabase.getAllCategory();
        adapter = new CategoryAdapter(getContext(), categories);
        listCategory.setAdapter(adapter);


        return fragment;
    }


}
