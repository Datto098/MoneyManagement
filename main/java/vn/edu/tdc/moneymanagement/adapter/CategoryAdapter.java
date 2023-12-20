package vn.edu.tdc.moneymanagement.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;
import vn.edu.tdc.moneymanagement.fragment.AddCategoryFragment;
import vn.edu.tdc.moneymanagement.fragment.AddFixedAccount;
import vn.edu.tdc.moneymanagement.fragment.AddSpendingFragment;
import vn.edu.tdc.moneymanagement.fragment.CategoryListFragment;
import vn.edu.tdc.moneymanagement.model.Category;
import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.SpendingAccount;

public class CategoryAdapter extends RecyclerView.Adapter {

    private  ArrayList<Category> categories;
    private LayoutInflater inflater;
    private Context context;
    public static Category category;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
        this.context = context;
    }

    public CategoryAdapter(){

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        Category category1 = categories.get(position);
        long idImage = category1.getIcon();
        holder1.imageView.setImageResource((int) idImage);
        holder1.lblCategory.setText(category1.getContent());

        holder1.linearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = category1;
                FragmentManager fragmentManager =((AppCompatActivity)context).getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
            }
        });

        holder1.linearItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AddCategoryFragment fragment = new AddCategoryFragment(category);
                FragmentTransaction transaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view_tag,fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearItem;

        TextView lblCategory;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            lblCategory = itemView.findViewById(R.id.lblCategory);
            imageView = itemView.findViewById(R.id.imageIcon);
            linearItem = itemView.findViewById(R.id.linearItem);
        }
    }

}
