package com.mayo.openhouse.openhouse;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mayo.openhouse.OpenHouse;
import com.mayo.openhouse.R;
import com.mayo.openhouse.Tag;
import com.mayo.openhouse.datamodel.Openhouse;
import com.mayo.openhouse.datamodel.Property;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OpenhouseDetailsListener} interface
 * to handle interaction events.
 */
public class FragOpenhouseDetails extends Fragment implements AbsListView.OnItemClickListener {

    private OpenhouseDetailsListener mListener;
    private int position;
    private Openhouse mOpenhouse;
    private SimpleDateFormat formatter;

    private AbsListView mListView;
    private PropertyAdapter mAdapter;

    public FragOpenhouseDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        formatter = new SimpleDateFormat("d MMM");
        if (getArguments() != null) {
            position = getArguments().getInt("pos");
            mOpenhouse = OpenHouse.getInstance().mOpenhouse.get(getArguments().getInt("pos"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_openhouse_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.openhouse_name)).setText(mOpenhouse.getString("name"));
        ((TextView) view.findViewById(R.id.openhouse_date)).setText(formatter.format(mOpenhouse.getDate("valid_date")));

        final TextView name = (TextView) view.findViewById(R.id.by_user);

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("objectId", mOpenhouse.getString("agent"));
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null)
                    name.setText("Agent: " + user.getString("name"));
                else
                    Log.i(Tag.LOG, "E: " + e.getMessage());
            }
        });

        mAdapter = new PropertyAdapter(getActivity());

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        ArrayList<String> properties = mOpenhouse.getProperties();
        OpenHouse.getInstance().mProperty.clear();
        for (String property : properties) {

            ParseQuery<Property> propertyQuery = ParseQuery.getQuery(Property.class);
            propertyQuery.whereEqualTo("objectId", property);
            propertyQuery.getFirstInBackground(new GetCallback<Property>() {
                @Override
                public void done(Property proper, ParseException e) {
                    if (e == null) {
                        OpenHouse.getInstance().mProperty.add(proper);
//                        mAdapter.setData(OpenHouse.getInstance().mProperty);
                        mAdapter.notifyDataSetChanged();
                    }else
                        Log.i(Tag.LOG, "E: " + e.getMessage());
                }
            });
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OpenhouseDetailsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OpenhouseDetailsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mListener != null) {
            mListener.showPropertyDetails(position);
        }
    }

    public interface OpenhouseDetailsListener {
        void showPropertyDetails(int pos);
    }

}
