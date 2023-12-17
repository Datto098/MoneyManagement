package vn.edu.tdc.moneymanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.ListAdapter;
import vn.edu.tdc.moneymanagement.R;

public class AddFixedAccount extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.add_fixed_account_fragment, container, false);

        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerviewParent);
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Test");

        ListAdapter listAdapter = new ListAdapter(getContext(), list);
        recyclerView.setAdapter(listAdapter);

        return fragment;
    }
}
