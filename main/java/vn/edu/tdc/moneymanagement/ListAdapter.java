package vn.edu.tdc.moneymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ListAdapter extends RecyclerView.Adapter {
    private final ArrayList<String> colors;
    private final LayoutInflater inflater;

    public ListAdapter(Context context, ArrayList<String> colors) {
        this.inflater = LayoutInflater.from(context);
        this.colors = colors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Test");

        ListItemAdapter listItemAdapter = new ListItemAdapter(inflater.getContext(), list);
        holder1.recyclerViewChild.setAdapter(listItemAdapter);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        RecyclerView recyclerViewChild;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerviewParent);
            recyclerViewChild = itemView.findViewById(R.id.recyclerviewChild);
        }
    }
}


