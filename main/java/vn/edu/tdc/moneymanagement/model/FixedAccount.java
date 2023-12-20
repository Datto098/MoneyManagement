package vn.edu.tdc.moneymanagement.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class FixedAccount {

    public final static String TABLE_NAME = "fixed_accounts";
    public final static String ID = "_id";
    public final static String MONEY = "money";
    public final static String CONTENT = "content";
    public final static String DATE = "date";

    private int id;
    private long money;
    private String content;
    private LocalDate date;

    public FixedAccount(int id, long money, String content, LocalDate date) {
        this.id = id;
        this.money = money;
        this.content = content;
        this.date = date;
    }

    public FixedAccount() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return id +
                " # " + money +
                " # " + content +
                " # " + date;
    }

//    // Thêm lớp TypeConverter
//    public static class Converters {
//        @TypeConverter
//        public static String dateToString(LocalDate date) {
//            String result = date == null ? null : date.toString();
////            Log.d("test", "dateToString: " + result);
//            return result;
//        }
//
//        @TypeConverter
//        public static LocalDate stringToDate(String dateString) {
//            LocalDate result = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                result = dateString == null ? null : LocalDate.parse(dateString);
//            }
//            return result;
//        }
//    }
}
