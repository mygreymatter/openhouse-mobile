package com.mayo.openhouse.openhouse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mayo.openhouse.OpenHouse;
import com.mayo.openhouse.R;
import com.mayo.openhouse.Tag;
import com.mayo.openhouse.datamodel.Openhouse;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mayo on 1/11/15.
 */
public class OpenhouseAdapter extends BaseAdapter {

    private final SimpleDateFormat formatter;
    private OpenHouse mOpenHouse;
    private LayoutInflater mInflater;
    private Openhouse openhouse;

    public OpenhouseAdapter(Context context){
        mOpenHouse = OpenHouse.getInstance();
        mInflater = LayoutInflater.from(context);

        formatter = new SimpleDateFormat("d MMM");

    }

    public void setData(List<Openhouse> openhouses){
        mOpenHouse.mOpenhouse = openhouses;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mOpenHouse.mOpenhouse.size();
    }

    @Override
    public Openhouse getItem(int position) {
        return mOpenHouse.mOpenhouse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position,View view, ViewGroup parent) {
        if(view == null)
            view = mInflater.inflate(R.layout.r_openhouse,parent,false);

        openhouse = getItem(position);
                ((TextView) view.findViewById(R.id.openhouse_name)).setText(openhouse.getString("name"));
        ((TextView) view.findViewById(R.id.openhouse_date)).setText(formatter.format(openhouse.getDate("valid_date")));
        final TextView name = (TextView) view.findViewById(R.id.by_user);

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("objectId",openhouse.getString("agent"));
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null)
                    name.setText("Agent: "+user.getString("name"));
                else
                    Log.i(Tag.LOG, "E: " + e.getMessage());
            }
        });

        return view;
    }
}
