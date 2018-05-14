package com.example.nischal.homkrunmenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/*
 * Created by nischal on 4/2/18.
 */

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {
    class ViewHolder {
        public TextView titleTv;
        public TextView descriptionTv;
        public TextView countTv;
        public ImageView plusOneIv;
        public ImageView minusOneIv;
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
            mViewHolder.titleTv = (TextView) convertView.findViewById(R.id.menuItemTitle);
            mViewHolder.descriptionTv = (TextView) convertView.findViewById(R.id.menuItemDescription);
            mViewHolder.countTv = (TextView) convertView.findViewById(R.id.menuItemCount);
            mViewHolder.plusOneIv = (ImageView) convertView.findViewById(R.id.plusOne);
            mViewHolder.minusOneIv = (ImageView) convertView.findViewById(R.id.minusOne);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        final TextView finalCountTv = ((TextView)convertView.findViewById(R.id.menuItemCount));

        final MenuItem menuItem = getItem(position);
        String description = menuItem.getPrice()+"";

        mViewHolder.titleTv.setText(menuItem.getTitle());
        mViewHolder.descriptionTv.setText(description);
        mViewHolder.countTv.setText(menuItem.getCount()+"");

        mViewHolder.plusOneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItem.countPlusOne();
                finalCountTv.setText(menuItem.getCount()+"");
            }
        });

        mViewHolder.minusOneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItem.countMinusOne();
                finalCountTv.setText(menuItem.getCount()+"");
            }
        });

        return convertView;
    }
}
