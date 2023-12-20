package vn.edu.tdc.moneymanagement.model;

import java.time.LocalDate;

public class SpendingAccount {
    public final static String TABLE_NAME = "spending_accounts";
    public final static String ID = "_id";
    public final static String MONEY = "money";
    public final static String CATEGORY = "category";
    public final static String DATE = "date";
    public final static String CATEGORY_ID = "category_id";

    private int id;
    private long money;
    private Category category;
    private LocalDate date;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    //Constructor
    public SpendingAccount(int id, long money, Category category, LocalDate date) {
        this.id = id;
        this.money = money;
        this.category = category;
        this.date = date;
    }

    public SpendingAccount() {
    }

    @Override
    public String toString() {
        return "SpendingAccount{" +
                "id=" + id +
                ", money=" + money +
                ", category=" + category.toString() +
                ", date=" + date +
                '}';
    }
}
