package vn.edu.tdc.moneymanagement.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = TotalMoney.TABLE_NAME)
public class TotalMoney {

    //Dinh nghia thuoc tinh cua bang
    @Ignore
    public final static String TABLE_NAME = "total_money";
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

    public TotalMoney() {

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

}
