package vn.edu.tdc.moneymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.tdc.edu.moneymanagement.R;

public class ListItemAdapter extends RecyclerView.Adapter {
    private ArrayList<String> colors;
    private LayoutInflater inflater;

    public ListItemAdapter (Context context, ArrayList<String> colors){
        this.inflater = LayoutInflater.from(context);
        this.colors = colors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.recyclerview_item_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public ViewHolder(View itemView){
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerviewChild);
        }
    }
}


