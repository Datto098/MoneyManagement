package vn.edu.tdc.moneymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;

public class CustomAdapter extends ArrayAdapter<Integer> {
    private Context context;
    private ArrayList<Integer> icons;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Integer> objects) {
        super(context, resource, objects);
        icons = objects;
        this.context = context;
    }

    static class ViewHolder {
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // If convertView is null, inflate the layout
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_grid_view, parent, false);

            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.viewImageIcon);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(holder);
        } else {
            // If convertView is not null, reuse the ViewHolder from the tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the image resource for the ImageView
        holder.imageView.setImageResource(icons.get(position));

        // Output
        return convertView;
    }
}
