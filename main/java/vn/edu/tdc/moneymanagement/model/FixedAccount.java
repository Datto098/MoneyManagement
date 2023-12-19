package vn.edu.tdc.moneymanagement.model;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

@Entity(tableName = FixedAccount.TABLE_NAME)
public class FixedAccount {

    //Dinh nghia thuoc tinh cua bang
    @Ignore
    public final  static String TABLE_NAME = "fixed_accounts";
    @Ignore
    public final static String ID = "_id";
    @Ignore
    public final static String MONEY = "money";
    @Ignore
    public final static String CONTENT = "content";
    @Ignore
    public final static String DATE = "date";


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    private int id;
    @ColumnInfo(name = MONEY)
    private long money;
    @ColumnInfo(name = CONTENT)
    private String content;
    @ColumnInfo(name = DATE)
    private LocalDate date;

    public FixedAccount(){

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

    // Thêm lớp TypeConverter
    public static class Converters {
        @TypeConverter
        public static String dateToString(LocalDate date) {
            String result = date == null ? null : date.toString();
//            Log.d("test", "dateToString: " + result);
            return result;
        }
        @TypeConverter
        public static LocalDate stringToDate(String dateString) {
            LocalDate result = dateString == null ? null : LocalDate.parse(dateString);
            return result;
        }
    }



    @Override
    public String toString() {
        return id +
                " # "  + money +
                " # " + content +
                " # " + date;
    }
}
