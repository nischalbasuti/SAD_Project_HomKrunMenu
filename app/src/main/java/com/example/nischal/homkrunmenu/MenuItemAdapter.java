package com.example.nischal.homkrunmenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/*
 * Created by nischal on 4/2/18.
 */

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {
    class ViewHolder {
        public TextView title;
        public TextView description;
    }

    private ViewHolder mViewHolder;
    private LayoutInflater mLayoutInflater;

    MenuItemAdapter(@NonNull Context context, List<MenuItem> resource) {
        super(context, R.layout.menu_item,resource);
        mLayoutInflater = LayoutInflater.from(getContext());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.menu_item, parent, false);

            mViewHolder = new ViewHolder();
            mViewHolder.title = (TextView) convertView.findViewById(R.id.menuItemTitle);
            mViewHolder.description = (TextView) convertView.findViewById(R.id.menuItemDescription);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        MenuItem menuItem = getItem(position);
        String description = menuItem.getPrice()+"";
        mViewHolder.title.setText(menuItem.getTitle());
        mViewHolder.description.setText(description);

        return convertView;
    }
}
