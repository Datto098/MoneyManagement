package vn.edu.tdc.moneymanagement.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.tdc.moneymanagement.adapter.ListAdapter;
import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.model.ExpenseItem;

public class AddFixedAccount extends Fragment {

    private ArrayList<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();

    private long money;
    private String content;
    private LocalDate date;
    private ListAdapter listAdapter;
    private EditText edtMoney;
    private EditText edtContent;
    private AppCompatButton btnSelectDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.add_fixed_account_fragment, container, false);

        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerviewParent);

        edtMoney =  fragment.findViewById(R.id.lblAmountMoney);
        edtContent = fragment.findViewById(R.id.lblContent);
        btnSelectDate = fragment.findViewById(R.id.lblDay);
        AppCompatButton btnAdd = fragment.findViewById(R.id.btnAdd);
        AppCompatButton btnUpdate = fragment.findViewById(R.id.btnUpdate);
        AppCompatButton btnDelete = fragment.findViewById(R.id.btnDelete);

        date = LocalDate.now();
        btnSelectDate.setText(date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                addExpense();
            }
        });


        ArrayList<ExpenseItem> list = expenseItems;

        listAdapter = new ListAdapter(getContext(), list);
        recyclerView.setAdapter(listAdapter);

        return fragment;
    }

    private void addExpense(){
        if(TextUtils.isEmpty(edtMoney.getText())){
            edtMoney.requestFocus();
            Toast.makeText(getContext(), "Vui lòng nhập dữ số tiền", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(edtContent.getText())){
            edtContent.requestFocus();
            Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
        }
        else {
            money = Long.parseLong(String.valueOf(edtMoney.getText()));
            content = edtContent.getText().toString();
            String selectDate = btnSelectDate.getText().toString();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                try {
                    // Parse the string to LocalDate
                    date = LocalDate.parse(selectDate, formatter);

                    // Now you can work with the LocalDate


                } catch (Exception e) {
                    // Handle the exception if the string is not in the expected format
                    System.out.println("Error parsing the date: " + e.getMessage());
                }

            }

            ExpenseItem expenseItem = new ExpenseItem(date, content, money);
            expenseItems.add(expenseItem);
            listAdapter.notifyDataSetChanged();

            edtMoney.setText("");
            edtContent.setText("");
            btnSelectDate.setText(R.string.select_day);
        }
    }


}
