package vn.edu.tdc.moneymanagement.model;

import java.time.LocalDate;

public class AbExpense {
    protected LocalDate date;
    protected String content;
    protected long totalMoney;

    public AbExpense(LocalDate date, String content, long totalMoney) {
        this.date = date;
        this.content = content;
        this.totalMoney = totalMoney;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public long getTotalMoney() {
        return totalMoney;
    }
}
