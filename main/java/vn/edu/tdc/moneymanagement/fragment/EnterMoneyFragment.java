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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.TotalMoney;

public class EnterMoneyFragment extends Fragment {

    public static EditText edtMoney;
    public static EditText edtContent;
    public static AppCompatButton btnSelectDate;
    public static String prevTitle = "Tổng tiền";
    public static String currentTitle = "Thêm tổng tiền";
    private long money;
    private String content;
    private LocalDate date;
    private MyDatabase myDatabase;
    private TotalMoney totalMoney;


    public EnterMoneyFragment(TotalMoney totalMoney) {
        this.totalMoney = totalMoney;
    }

    public EnterMoneyFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(vn.edu.tdc.moneymanagement.R.layout.enter_money_fragment, container, false);

        //innit
        myDatabase = new MyDatabase(getContext());

        // get input values
        edtMoney = fragment.findViewById(R.id.lblAmountMoney);
        edtContent = fragment.findViewById(R.id.lblContent);
        btnSelectDate = fragment.findViewById(R.id.lblDay);
        AppCompatButton btnAdd = fragment.findViewById(R.id.btnAdd);
        AppCompatButton btnUpdate = fragment.findViewById(R.id.btnUpdate);
        AppCompatButton btnDelete = fragment.findViewById(R.id.btnDelete);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int dayOfMonth = date.getDayOfMonth();
            int monthValue = date.getMonthValue();
            int year = date.getYear();

            // Kiểm tra và thêm số 0 nếu cần
            String dayString = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String monthString = (monthValue < 10) ? "0" + monthValue : String.valueOf(monthValue);

            // Sử dụng String.format để định dạng chuỗi
            String formattedDate = String.format("%s-%s-%d", dayString, monthString, year);

            // Đặt giá trị vào TextView
            btnSelectDate.setText(formattedDate);
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
                                // Tăng giá trị của selectedMonth vì tháng trong DatePicker được đánh số từ 0 đến 11
                                selectedMonth = selectedMonth + 1;

                                // Kiểm tra và thêm số 0 khi cần thiết
                                String dayString = (selectedDay < 10) ? "0" + selectedDay : String.valueOf(selectedDay);
                                String monthString = (selectedMonth < 10) ? "0" + selectedMonth : String.valueOf(selectedMonth);

                                // Tạo chuỗi ngày tháng năm
                                String selectedDate = dayString + "-" + monthString + "-" + selectedYear;

                                // Hiển thị ngày đã chọn trên nút hoặc nơi bạn muốn
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
        if (totalMoney != null) {
            setTotalMoney(totalMoney);
            // Update total money
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(edtMoney.getText())) {
                        edtContent.requestFocus();
                        Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(edtContent.getText())) {
                        edtContent.requestFocus();
                        Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
                    } else {
                        TotalMoney newTotalMoney = getTotalMoney();
                        newTotalMoney.setId(totalMoney.getId());

                        Log.d("TotalMoney", newTotalMoney.toString());

                        int check = myDatabase.updateTotalMoney(newTotalMoney);

                        if (check > 0) {
                            FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                            if (fragmentManager.getBackStackEntryCount() > 0) {
                                fragmentManager.popBackStack();
                            }
                        }
                    }
                }
            });

            // Delete total money
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDatabase.deleteTotalMoney(totalMoney.getId());
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    if (fragmentManager.getBackStackEntryCount() > 0) {
                        fragmentManager.popBackStack();
                    }
                }
            });

            btnAdd.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TotalMoney totalMoney = getTotalMoney();
                    Log.d("TotalMoney", totalMoney.toString());
                    if (totalMoney.getDate() != null) {
                        myDatabase.addTotalMoney(totalMoney);
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack();
                        }
                    }
                }
            });
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }


        return fragment;
    }

    private TotalMoney getTotalMoney() {
        Log.d("TotalMoneyEvent", "Click");
        TotalMoney totalMoney = new TotalMoney();
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
        return totalMoney;
    }

    private void setTotalMoney(TotalMoney totalMoney) {
        EnterMoneyFragment.edtMoney.setText(totalMoney.getMoney() + "");
        EnterMoneyFragment.edtContent.setText(totalMoney.getContent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate totalMoneyDate = totalMoney.getDate();
            int dayOfMonth = totalMoneyDate.getDayOfMonth();
            int monthValue = totalMoneyDate.getMonthValue();
            int year = totalMoneyDate.getYear();

            // Kiểm tra và thêm số 0 nếu cần
            String dayString = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String monthString = (monthValue < 10) ? "0" + monthValue : String.valueOf(monthValue);

            // Sử dụng String.format để định dạng chuỗi
            String formattedDate = String.format("%s-%s-%d", dayString, monthString, year);

            // Đặt giá trị vào TextView
            EnterMoneyFragment.btnSelectDate.setText(formattedDate);
        }
    }

    private void clear() {
        edtMoney.setText("");
        edtContent.setText("");
    }
}
