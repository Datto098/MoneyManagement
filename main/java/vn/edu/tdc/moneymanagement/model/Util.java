package vn.edu.tdc.moneymanagement.model;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatButton;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Util {


    //Ham convert chuyen chuoi thanh localDate
    public static LocalDate convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


    //Ham lay ngay bat dau va ngay ket thuc
    public static void getStartDayAndEndDay(View fragment, AppCompatButton btnStartDay, AppCompatButton btnEndDay) {
        btnStartDay.setOnClickListener(new View.OnClickListener() {
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
                                btnStartDay.setText(selectedDate);
                            }

                        },
                        year, month, day
                );

                // DatePickerDialog
                datePickerDialog.show();
            }
        });
        btnEndDay.setOnClickListener(new View.OnClickListener() {
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
                                btnEndDay.setText(selectedDate);
                            }
                        },
                        year, month, day
                );

                // DatePickerDialog
                datePickerDialog.show();
            }
        });
    }

    //Ham fomat so
    public static String formatNumber(long number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        return formattedNumber;
    }

    public static boolean isValidDateFormat(String inputDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        try {
            // Cố gắng chuyển đổi chuỗi thành đối tượng Date
            Date parsedDate = dateFormat.parse(inputDate);

            // Nếu không có ngoại lệ, định dạng là hợp lệ
            return true;
        } catch (ParseException e) {
            // Nếu có ngoại lệ, định dạng không hợp lệ
            return false;
        }
    }

}
