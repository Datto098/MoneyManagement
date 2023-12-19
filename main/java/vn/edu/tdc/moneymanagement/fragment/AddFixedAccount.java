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
import vn.edu.tdc.moneymanagement.adapter.ListAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabaseAPIs;
import vn.edu.tdc.moneymanagement.model.FixedAccount;

public class AddFixedAccount extends Fragment {

    public static ArrayList<FixedAccount> fixedAccounts;
    public static EditText edtMoney;
    public static EditText edtContent;
    public static AppCompatButton btnSelectDate;
    public static AppCompatButton btnAdd;
    private static ListAdapter listAdapter;
    private long money;
    private String content;
    private LocalDate date;
    private MyDatabaseAPIs databaseAPIs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.add_fixed_account_fragment, container, false);

        //innit
        databaseAPIs = new MyDatabaseAPIs(getContext());

        //Thuc hien  su ly uy queen
        databaseAPIs.setCompleteListener(new MyDatabaseAPIs.CompleteListener() {
            @Override
            public void notifyToActivity(int notificationID) {
                if (notificationID == MyDatabaseAPIs.SAY_DONE) {
                    listAdapter.notifyDataSetChanged();
                    clear();
                } else if (notificationID == MyDatabaseAPIs.DELETE_DONE) {
                    listAdapter.notifyDataSetChanged();
                } else if (notificationID == MyDatabaseAPIs.UPDATE_DONE) {
                    listAdapter.notifyDataSetChanged();
                } else if (notificationID == MyDatabaseAPIs.GET_ALL_DONE) {
                    listAdapter.notifyDataSetChanged();
                }
            }
        });


        //Doc du lieu  tu database neu co
        fixedAccounts = new ArrayList<FixedAccount>();

        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerviewParent);

        edtMoney = fragment.findViewById(R.id.lblAmountMoney);
        edtContent = fragment.findViewById(R.id.lblContent);
        btnSelectDate = fragment.findViewById(R.id.lblDay);
        btnAdd = fragment.findViewById(R.id.btnAdd);
        AppCompatButton btnUpdate = fragment.findViewById(R.id.btnUpdate);
        AppCompatButton btnDelete = fragment.findViewById(R.id.btnDelete);

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FixedAccount fixedAccount = getFixedAccount();
                if (fixedAccount.getDate() != null) {
                    databaseAPIs.saveFixedAccount(fixedAccount);
                    fixedAccounts.add(fixedAccount);
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = ListAdapter.selectedRow;
                if (current != -1) {
                    FixedAccount fixedAccount = getFixedAccount();
                    fixedAccount.setId(fixedAccounts.get(current).getId());
                    databaseAPIs.updateFixedAccount(fixedAccount);
                    fixedAccounts.set(current, fixedAccount);
                }


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListAdapter.selectedRow != -1) {
                    FixedAccount fixedAccount = fixedAccounts.get(ListAdapter.selectedRow);
                    databaseAPIs.deleteFixedAccount(fixedAccount);
                    Log.d("test", fixedAccount.toString());
                    fixedAccounts.remove(ListAdapter.selectedRow);
                    clear();
                }
            }
        });


        databaseAPIs.getAllFixedAccount(fixedAccounts);

        listAdapter = new ListAdapter(getContext(), fixedAccounts);
        recyclerView.setAdapter(listAdapter);

        return fragment;
    }

    private FixedAccount getFixedAccount() {
        FixedAccount fixedAccount = new FixedAccount();
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

            fixedAccount.setContent(content);
            fixedAccount.setDate(date);
            fixedAccount.setMoney(money);


        }
        return fixedAccount;
    }

    private void clear() {
        edtMoney.setText("");
        edtContent.setText("");
    }


}
