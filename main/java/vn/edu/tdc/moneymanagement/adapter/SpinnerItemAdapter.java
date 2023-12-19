package vn.edu.tdc.moneymanagement.adapter;

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

    private final Context context;
    private final ArrayList<String> arrayList;
    private final ArrayList<Integer> icons;

    public SpinnerItemAdapter(Context context, ArrayList<String> arrayList, ArrayList<Integer> icons) {
        this.context = context;
        this.arrayList = arrayList;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        TextView lblCategory = view.findViewById(R.id.lblCategory);

        imageView.setImageResource(icons.get(i));
        lblCategory.setText(arrayList.get(i));

        return view;
    }
}
