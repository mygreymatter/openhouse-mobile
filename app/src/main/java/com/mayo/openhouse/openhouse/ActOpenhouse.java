package com.mayo.openhouse.openhouse;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mayo.openhouse.R;
import com.mayo.openhouse.openhouse.FragOpenhouseDetails.OpenhouseDetailsListener;
import com.mayo.openhouse.openhouse.FragOpenhouseList.OpenhouseListListener;

public class ActOpenhouse extends AppCompatActivity
    implements OpenhouseListListener,OpenhouseDetailsListener{

    private FragmentManager mFragManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_openhouse_list);

        mFragManager = getFragmentManager();
        if(savedInstanceState == null)
            mFragManager.beginTransaction().add(R.id.container, FragOpenhouseList.newInstance()).commit();

    }

    @Override
    public void showDetails(int pos) {
        FragOpenhouseDetails frag = new FragOpenhouseDetails();
        Bundle args = new Bundle();
        args.putInt("pos", pos);

        frag.setArguments(args);
        mFragManager.beginTransaction().replace(R.id.container, frag).addToBackStack("openhouse_details").commit();
    }

    @Override
    public void showPropertyDetails(int pos) {
        FragPropertyDetails frag = new FragPropertyDetails();
        Bundle args = new Bundle();
        args.putInt("pos",pos);

        frag.setArguments(args);
        mFragManager.beginTransaction().replace(R.id.container, frag).addToBackStack("property_details").commit();
    }

    @Override
    public void onBackPressed() {
        if(mFragManager.getBackStackEntryCount() > 0) {
            mFragManager.popBackStackImmediate();
        }else
            super.onBackPressed();
    }
}
