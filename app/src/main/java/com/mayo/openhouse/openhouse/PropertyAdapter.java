package com.mayo.openhouse.openhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayo.openhouse.OpenHouse;
import com.mayo.openhouse.R;
import com.mayo.openhouse.datamodel.Property;
import com.parse.ParseFile;

import java.util.List;

/**
 * Created by mayo on 1/11/15.
 */
public class PropertyAdapter extends BaseAdapter {

    private OpenHouse mOpenHouse;
    private LayoutInflater mInflater;
    private Property property;

    public PropertyAdapter(Context context){
        mOpenHouse = OpenHouse.getInstance();
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<Property> property){
        mOpenHouse.mProperty = property;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mOpenHouse.mProperty.size();
    }

    @Override
    public Property getItem(int position) {
        return mOpenHouse.mProperty.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position,View view, ViewGroup parent) {
        if(view == null)
            view = mInflater.inflate(R.layout.r_property,parent,false);

        property = getItem(position);
        ((TextView) view.findViewById(R.id.openhouse_name)).setText(property.getString("title"));
        ((TextView) view.findViewById(R.id.openhouse_date)).setText(property.getString("location"));
        ((TextView) view.findViewById(R.id.by_user)).setText(String.valueOf(property.get("price")));

        ParseFile image = property.getParseFile("property_image");

        final ImageView imageView = (ImageView) view.findViewById(R.id.property_thumb);
        Glide.with(parent.getContext()).load(image.getUrl()).override(200,200).centerCrop().into(imageView);

        return view;
    }
}
