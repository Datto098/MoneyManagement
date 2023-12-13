package vn.edu.tdc.moneymanagement.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.R;

public class SpinnerItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> categories;
    ArrayList<Integer> icons;

    public SpinnerItemAdapter(Context context, ArrayList<String> categories, ArrayList<Integer> icons) {
        this.context = context;
        this.categories = categories;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.spinner_item_layout, viewGroup, false);
        ImageView imageView = view.findViewById(R.id.imageIcon);
        TextView textView = view.findViewById(R.id.lblCategory);

        imageView.setImageResource(icons.get(i));
        textView.setText(categories.get(i));
        return view;
    }
}
