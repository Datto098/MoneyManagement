package vn.edu.tdc.moneymanagement.model;

import java.time.LocalDate;
public class TotalMoney {

    //Dinh nghia thuoc tinh cua bang
    public final static String TABLE_NAME = "total_money";
    public final static String ID = "_id";
    public final static String MONEY = "money";
    public final static String CONTENT = "content";
    public final static String DATE = "date";


    private int id;
    private long money;
    private String content;
    private LocalDate date;

    public TotalMoney() {

    }

    public TotalMoney(int id, long money, String content, LocalDate date) {
        this.id = id;
        this.money = money;
        this.content = content;
        this.date = date;
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
