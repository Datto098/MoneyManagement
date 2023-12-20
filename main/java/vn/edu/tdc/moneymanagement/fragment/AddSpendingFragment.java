package vn.edu.tdc.moneymanagement.fragment;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.adapter.CategoryAdapter;
import vn.edu.tdc.moneymanagement.database.MyDatabase;
import vn.edu.tdc.moneymanagement.model.Category;
import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.SpendingAccount;
import vn.edu.tdc.moneymanagement.model.Util;

public class AddSpendingFragment extends Fragment {

    private static EditText edtMoney;
    private Category category;
    private AppCompatButton btnSelectDate;
    private SpendingAccount spendingAccount;
    private MyDatabase myDatabase;

    public AddSpendingFragment(){}
    private AppCompatButton btnCategory;
    private TextView lblContent;
    private ImageView imageCategory;


    public AddSpendingFragment(SpendingAccount spendingAccount){
        this.spendingAccount = spendingAccount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.add_expense_fragment, container, false);

        //init database
        myDatabase = new MyDatabase(getContext());

        //Get button from layout
        btnCategory = fragment.findViewById(R.id.btnCategory);
        AppCompatButton btnSave = fragment.findViewById(R.id.btnSave);
        AppCompatButton btnUpdate = fragment.findViewById(R.id.btnUpdate);
        AppCompatButton btnDelete = fragment.findViewById(R.id.btnDelete);
        btnSelectDate = fragment.findViewById(R.id.btnSelectDay);
        lblContent = fragment.findViewById(R.id.lblContent);
        imageCategory = fragment.findViewById(R.id.imageIcon);
        LinearLayout itemCategory = fragment.findViewById(R.id.itemCategory);
        View viewLine = fragment.findViewById(R.id.viewLine);
        edtMoney = fragment.findViewById(R.id.edtMoney);

        itemCategory.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);

        LocalDate date = LocalDate.now();
        btnSelectDate.setText(date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear());

        if(CategoryAdapter.category != null){
            category = CategoryAdapter.category;
            Log.d("test", category.toString());
        }

        if(category != null) {
            btnCategory.setVisibility(View.GONE);
            itemCategory.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            lblContent.setText(category.getContent());
            imageCategory.setImageResource((int)category.getIcon());

            itemCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CategoryListFragment fragment = new CategoryListFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_view_tag, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });

        }

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryListFragment fragment = new CategoryListFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
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


        if(spendingAccount != null){
            setSpendingAccount(spendingAccount);
            btnCategory.setVisibility(View.GONE);
            itemCategory.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            if(category != null) {
                lblContent.setText(category.getContent());
                imageCategory.setImageResource((int) category.getIcon());
            }

                itemCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CategoryListFragment fragment = new CategoryListFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container_view_tag, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpendingAccount spendingAccount1 = getSpendingAccount();
                    spendingAccount1.setId(spendingAccount.getId());
                    int check = myDatabase.updateSpendingAccount(spendingAccount1);
                    if(check >0){
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
                  int check =  myDatabase.deleteSpendingAccount(spendingAccount.getId());
                    if(check >0){
                        FragmentManager fragmentManager =((AppCompatActivity) getContext()).getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack();
                        }
                    }
                }
            });

            btnDelete.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
        }
        else{

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpendingAccount spendingAccount1 = getSpendingAccount();
                    long check = myDatabase.addSpendingAccount(spendingAccount1);
                    if(check >0){
                        FragmentManager fragmentManager =((AppCompatActivity) getContext()).getSupportFragmentManager();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack();
                        }
                    }
                }
            });
            btnDelete.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);


        }
        return fragment;
    }

    private SpendingAccount getSpendingAccount() {
        SpendingAccount spendingAccount = new SpendingAccount();
        long money = 0;
        LocalDate date = LocalDate.now();

        if (TextUtils.isEmpty(edtMoney.getText())) {
            edtMoney.requestFocus();
            Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(btnCategory.getText())) {
            Toast.makeText(getContext(), "Vui lòng nội dung", Toast.LENGTH_LONG).show();
        } else {
            money = Long.parseLong(String.valueOf(edtMoney.getText()));
            String selectDate = btnSelectDate.getText().toString();
            date = Util.convertStringToDate(selectDate);
        }

        spendingAccount.setMoney(money);
        spendingAccount.setCategory(category);
        spendingAccount.setDate(date);
        Log.d("test", spendingAccount.toString());
        return spendingAccount;
    }

    private void setSpendingAccount(SpendingAccount spendingAccount) {
        edtMoney.setText(spendingAccount.getMoney() + "");
        lblContent.setText(spendingAccount.getCategory().getContent());
        imageCategory.setImageResource((int) spendingAccount.getCategory().getIcon());
        btnSelectDate.setText(spendingAccount.getDate().getDayOfMonth() + "-" + spendingAccount.getDate().getMonthValue() + "-" + spendingAccount.getDate().getYear());

    }
}
