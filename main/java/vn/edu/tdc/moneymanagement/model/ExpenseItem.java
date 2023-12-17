package vn.edu.tdc.moneymanagement.model;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class ExpenseItem extends AbExpense {
    private final int indexIcon;

    public ExpenseItem(LocalDate date, String content, long totalMoney, int indexIcon) {
        super(date, content, totalMoney);
        this.indexIcon = indexIcon;
    }

    public ExpenseItem(LocalDate date, String content, long totalMoney) {
        super(date, content, totalMoney);
        this.indexIcon = 0;
    }

    public int getIndexIcon() {
        return indexIcon;
    }

    @NonNull
    @Override
    public String toString() {
        return "ExpenseItem{" +
                "date=" + date +
                "content=" + content +
                "indexIcon=" + indexIcon +
                '}';
    }
}
