package com.mayo.openhouse.openhouse;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.mayo.openhouse.R;
import com.mayo.openhouse.Tag;
import com.mayo.openhouse.datamodel.Openhouse;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OpenhouseListListener}
 * interface.
 */
public class FragOpenhouseList extends Fragment implements AbsListView.OnItemClickListener {

    private OpenhouseListListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private OpenhouseAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static FragOpenhouseList newInstance() {
        FragOpenhouseList fragment = new FragOpenhouseList();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragOpenhouseList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new OpenhouseAdapter(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        queryOpenhouses();
    }

    private void queryOpenhouses() {
        ParseQuery<Openhouse> query = ParseQuery.getQuery(Openhouse.class);
        query.findInBackground(new FindCallback<Openhouse>() {
            @Override
            public void done(List<Openhouse> openhouses, ParseException e) {
                Log.i(Tag.LOG, "Openhouses: " + openhouses);
                if(openhouses != null){
                    for(Openhouse openhouse: openhouses)
                        Log.i(Tag.LOG, "" + openhouse.getString("name"));

                    mAdapter.setData(openhouses);

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OpenhouseListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OpenhouseListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.showDetails(position);
        }
    }

    public interface OpenhouseListListener {
        void showDetails(int pos);
    }

}
