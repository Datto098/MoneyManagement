package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.SpinnerItemAdapter;

public class AddSpendingFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static String prevTitle = "Các khoản chi tiêu";
    public static String currentTitle = "Thêm khoản chi mới";
    private ArrayList<String> categories;
    private ArrayList<Integer> icons;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.add_expense_fragment, container, false);

        categories = new ArrayList<String>();
        icons = new ArrayList<Integer>();


        //Add select category
        categories.add("Ăn uống");
        categories.add("Cafe");
        categories.add("Du lịch");

        icons.add(R.drawable.healthy_eating);
        icons.add(R.drawable.coffee_cup);
        icons.add(R.drawable.travel);

        //Get spinner from layout
        Spinner spinner = fragment.findViewById(R.id.spinnerCategory);

        spinner.setOnItemSelectedListener(this);

        SpinnerItemAdapter adapter = new SpinnerItemAdapter(getContext(), categories, icons);
        spinner.setAdapter(adapter);


        return fragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), categories.get(i), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
