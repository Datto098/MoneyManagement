package vn.edu.tdc.moneymanagement.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.tdc.moneymanagement.adapter.ListAdapter;
import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.model.ExpenseItem;

public class EnterMoneyFragment extends Fragment {

    private ArrayList<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();
    private ListAdapter listAdapter;
    private long money;
    private String content;
    private LocalDate date;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View fragment = inflater.inflate(vn.edu.tdc.moneymanagement.R.layout.enter_money_fragment, container, false);
        RecyclerView expenseRecyclerView = fragment.findViewById(R.id.recyclerViewMoney);


        // Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment.getContext());
        expenseRecyclerView.setLayoutManager(layoutManager);

        // get input values
        EditText edtMoney = (EditText) fragment.findViewById(R.id.lblAmountMoney);
        EditText edtContent = (EditText) fragment.findViewById(R.id.lblContent);
        AppCompatButton btnSelectDate = (AppCompatButton) fragment.findViewById(R.id.lblDay);


        AppCompatButton btnAdd = (AppCompatButton) fragment.findViewById(R.id.btnAdd);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        fragment.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String selectedDate = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                                btnSelectDate.setText(selectedDate);
                            }
                        },
                        year, month, day
                );

                // DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = Long.parseLong(String.valueOf(edtMoney.getText()));
                content = edtContent.getText().toString();
                String selectDate = btnSelectDate.getText().toString();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    try {
                        // Parse the string to LocalDate
                        date = LocalDate.parse(selectDate, formatter);

                        // Now you can work with the LocalDate
                        ExpenseItem expenseItem = new ExpenseItem(date, content, money);
                        expenseItems.add(expenseItem);


                    } catch (Exception e) {
                        // Handle the exception if the string is not in the expected format
                        System.out.println("Error parsing the date: " + e.getMessage());
                    }


                }

                listAdapter.notifyDataSetChanged();
                Log.d("money", money + "");
                Log.d("content", content + "");
                Log.d("selectDate", selectDate + "");

            }
        });

        ArrayList<ExpenseItem> list = expenseItems;

        listAdapter = new ListAdapter(getContext(), list);
        expenseRecyclerView.setAdapter(listAdapter);

        return fragment;
    }
}