package vn.edu.tdc.moneymanagement.model;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.databinding.CustomGridViewBinding;


public class CustomAdapter extends ArrayAdapter<Integer> {
    private final Activity context;
    ArrayList<Integer> icons;

    public CustomAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Integer> objects) {
        super(context, resource, objects);
        icons = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewBinding binding;

        //khai bao
        if (convertView == null) {
            binding = CustomGridViewBinding.inflate(context.getLayoutInflater(), parent, false);
            convertView = binding.getRoot();
            //gan ket doi tuong bidding voi tung listview item
            convertView.setTag(binding);
        } else {
            binding = (CustomGridViewBinding) convertView.getTag();
        }
        //
        ((CustomGridViewBinding) binding).viewImage.setImageResource(icons.get(position));
        //output
        return convertView;
    }
}
