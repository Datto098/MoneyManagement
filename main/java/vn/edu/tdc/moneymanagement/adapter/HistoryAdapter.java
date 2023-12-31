package vn.edu.tdc.moneymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.model.SpendingAccount;

public class HistoryAdapter extends RecyclerView.Adapter {

    private final ArrayList<SpendingAccount> spendingAccounts;
    private final LayoutInflater inflater;
    Context context;

    public HistoryAdapter(Context context, ArrayList<SpendingAccount> spendingAccounts) {
        this.inflater = LayoutInflater.from(context);
        this.spendingAccounts = spendingAccounts;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.expenses_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        SpendingAccount spendingAccount = spendingAccounts.get(position);
        String day = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            day = spendingAccount.getDate().getDayOfMonth() + "";
        }
        String monthAndYear = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            monthAndYear = spendingAccount.getDate().getMonthValue() + "/" + spendingAccount.getDate().getYear();
        }
        holder1.lblDay.setText(day);
        holder1.lblMonthAndYear.setText(monthAndYear);
        holder1.lblContent.setText(spendingAccount.getCategory().getContent());
        holder1.imageView.setImageResource((int) spendingAccount.getCategory().getIcon());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(spendingAccount.getMoney());

        holder1.lblMoney.setText(formattedNumber);
        int i = position;
    }

    @Override
    public int getItemCount() {
        return spendingAccounts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearItem;
        TextView lblDay;
        TextView lblMonthAndYear;
        TextView lblContent;
        TextView lblMoney;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            lblDay = itemView.findViewById(R.id.lblDay);
            lblMonthAndYear = itemView.findViewById(R.id.lblMonthAndYear);
            lblContent = itemView.findViewById(R.id.lbl_content);
            lblMoney = itemView.findViewById(R.id.lbl_money);
            imageView = itemView.findViewById(R.id.imageIcon);
            linearItem = itemView.findViewById(R.id.linearItem);
        }
    }
}
