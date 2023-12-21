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
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.ListAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.FixedAccount;

public class AddFixedAccount extends Fragment {

    public static EditText edtMoney;
    public static EditText edtContent;
    public static AppCompatButton btnSelectDate;
    public static String prevTitle = "Các khoản chi cố định";
    public static String currentTitle = "Thêm khoản chi mới";
    private static ListAdapter listAdapter;
    private long money;
    private String content;
    private LocalDate date;

    private MyDatabase myDatabase;
    private FixedAccount fixedAccount;

    private AppCompatButton btnAdd;
    private AppCompatButton btnUpdate;
    private AppCompatButton btnDelete;

    //Constructor
    public AddFixedAccount() {

    }

    public AddFixedAccount(FixedAccount fixedAccount) {
        this.fixedAccount = fixedAccount;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.add_fixed_account_fragment, container, false);
        //innit
        myDatabase = new MyDatabase(getContext());


        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerviewParent);

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

        if (fixedAccount != null) {
            setFixedAccount(fixedAccount);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(edtMoney.getText())) {
                        edtMoney.requestFocus();
                        Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(edtContent.getText())) {
                        edtContent.requestFocus();
                        Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
                    } else {
                        FixedAccount newFixedAccount = getFixedAccount();
                        newFixedAccount.setId(fixedAccount.getId());
                        int check = myDatabase.updateFixedAccount(newFixedAccount);

                        if (check > 0) {
                            FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                            if (fragmentManager.getBackStackEntryCount() > 0) {
                                fragmentManager.popBackStack();
                            }
                        }
                    }
                }
            });


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int check = myDatabase.deleteFixedAccount(fixedAccount.getId());

                    if (check > 0) {
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack();
                        }
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
                    FixedAccount fixedAccount = getFixedAccount();
                    if (fixedAccount.getDate() != null) {
                        long check = myDatabase.addFixedAccount(fixedAccount);
                        if (check != 0 && check != -1) {
                            FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                            if (fragmentManager.getBackStackEntryCount() > 0) {
                                fragmentManager.popBackStack();
                            }
                        }
                    }
                }
            });

            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        return fragment;
    }

    private FixedAccount getFixedAccount() {
        Log.d("Event", "Clicked");
        FixedAccount fixedAccount = new FixedAccount();
        money = Long.parseLong(String.valueOf(edtMoney.getText()));
        content = edtContent.getText().toString();
        String selectDate = btnSelectDate.getText().toString();


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                // Parse the string to LocalDate
                date = LocalDate.parse(selectDate, formatter);

                // Now you can work with the LocalDate
                fixedAccount.setContent(content);
                fixedAccount.setDate(date);
                fixedAccount.setMoney(money);

            } catch (Exception e) {
                // Handle the exception if the string is not in the expected format
                System.out.println("Error parsing the date: " + e.getMessage());
            }
        }
        return fixedAccount;
    }

    //Ham set
    private void setFixedAccount(FixedAccount fixed) {
        AddFixedAccount.edtMoney.setText(fixed.getMoney() + "");
        AddFixedAccount.edtContent.setText(fixed.getContent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Nếu không null, lấy giá trị ngày, tháng, năm
            int dayOfMonth = fixed.getDate().getDayOfMonth();
            int monthValue = fixed.getDate().getMonthValue();
            int year = fixed.getDate().getYear();

            // Kiểm tra và thêm số 0 nếu cần
            String dayString = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String monthString = (monthValue < 10) ? "0" + monthValue : String.valueOf(monthValue);

            // Sử dụng String.format để định dạng chuỗi
            String formattedDate = String.format("%s-%s-%d", dayString, monthString, year);

            // Đặt giá trị vào TextView
            AddFixedAccount.btnSelectDate.setText(formattedDate);
        }
    }

    private void clear() {
        edtMoney.setText("");
        edtContent.setText("");
    }


}
