package vn.edu.tdc.moneymanagement.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.MoneyAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabaseAPIs;
import vn.edu.tdc.moneymanagement.model.TotalMoney;

public class EnterMoneyFragment extends Fragment {

    public static EditText edtMoney;
    public static EditText edtContent;
    public static AppCompatButton btnSelectDate;
    public static AppCompatButton btnAdd;
    public static AppCompatButton btnUpdate;
    public static AppCompatButton btnDelete;
    private ArrayList<TotalMoney> totalMonies;
    private MoneyAdapter listAdapter;
    private long money;
    private String content;
    private LocalDate date;
    private MyDatabaseAPIs databaseAPIs;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(vn.edu.tdc.moneymanagement.R.layout.enter_money_fragment, container, false);

        databaseAPIs = new MyDatabaseAPIs(getContext());
        databaseAPIs.setCompleteListener(new MyDatabaseAPIs.CompleteListener() {
            @Override
            public void notifyToActivity(int notificationID) {
                if (notificationID == MyDatabaseAPIs.SAVE_TOTAL_MONEY_DONE) {
                    listAdapter.notifyDataSetChanged();
                    clear();
                } else if (notificationID == MyDatabaseAPIs.DELETE_TOTAL_MONEY_DONE) {
                    listAdapter.notifyDataSetChanged();
                } else if (notificationID == MyDatabaseAPIs.UPDATE_TOTAL_MONEY_DONE) {
                    listAdapter.notifyDataSetChanged();
                } else if (notificationID == MyDatabaseAPIs.GET_TOTAL_MONEY_DONE) {
                    listAdapter.notifyDataSetChanged();
                }
            }
        });

        RecyclerView expenseRecyclerView = fragment.findViewById(R.id.recyclerViewMoney);
        totalMonies = new ArrayList<>();

        // get input values
        edtMoney = fragment.findViewById(R.id.lblAmountMoney);
        edtContent = fragment.findViewById(R.id.lblContent);
        btnSelectDate = fragment.findViewById(R.id.lblDay);
        btnAdd = fragment.findViewById(R.id.btnAdd);
        btnUpdate = fragment.findViewById(R.id.btnUpdate);
        btnDelete = fragment.findViewById(R.id.btnDelete);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            btnSelectDate.setText(date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear());
        }

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

        // Add total money
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalMoney totalMoney = getTotalMoney();
                if (totalMoney.getDate() != null) {
                    databaseAPIs.saveTotalMoney(totalMoney);
                    totalMonies.add(totalMoney);
                }
            }
        });

        // Update total money
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "click");
                int current = MoneyAdapter.selectedRow;
                Log.d("index", current + "");

                if (current != -1) {
                    TotalMoney totalMoney = getTotalMoney();
                    Log.d("test", totalMoney.toString());
                    totalMoney.setId(totalMonies.get(current).getId());
                    databaseAPIs.updateTotalMoney(totalMoney);
                    totalMonies.set(current, totalMoney);
                }
            }
        });

        // Delete total money
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoneyAdapter.selectedRow != -1) {
                    TotalMoney totalMoney = totalMonies.get(MoneyAdapter.selectedRow);
                    databaseAPIs.deleteTotalMoney(totalMoney);
                    Log.d("test", totalMoney.toString());
                    totalMonies.remove(MoneyAdapter.selectedRow);
                    clear();
                }
            }
        });
        databaseAPIs.getAllTotalMoney(totalMonies);

        listAdapter = new MoneyAdapter(getContext(), totalMonies);
        expenseRecyclerView.setAdapter(listAdapter);

        return fragment;
    }


    private TotalMoney getTotalMoney() {
        TotalMoney totalMoney = new TotalMoney();
        if (TextUtils.isEmpty(edtMoney.getText())) {
            edtContent.requestFocus();
            Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edtContent.getText())) {
            edtContent.requestFocus();
            Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
        } else {
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

            totalMoney.setContent(content);
            totalMoney.setDate(date);
            totalMoney.setMoney(money);


        }
        return totalMoney;
    }

    private void clear() {
        edtMoney.setText("");
        edtContent.setText("");
    }
}
