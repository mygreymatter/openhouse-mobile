package com.mayo.openhouse.openhouse;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayo.openhouse.OpenHouse;
import com.mayo.openhouse.R;
import com.mayo.openhouse.datamodel.Property;
import com.parse.ParseFile;


public class FragPropertyDetails extends Fragment {

    private Property mProperty;

    public FragPropertyDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int pos = getArguments().getInt("pos");
        mProperty = OpenHouse.getInstance().mProperty.get(pos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_property_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.property_title)).setText(mProperty.getString("title"));
        ((TextView) view.findViewById(R.id.property_category)).setText("Type: " + mProperty.getString("category"));
        ((TextView) view.findViewById(R.id.property_facing)).setText("Facing: " + mProperty.getString("facing_side"));
        ((TextView) view.findViewById(R.id.property_floors)).setText("Floors: " + mProperty.getInt("no_of_floors"));
        ((TextView) view.findViewById(R.id.property_description)).setText("Description: " + mProperty.getString("major_things_available"));
        ((TextView) view.findViewById(R.id.property_parking)).setText("Parking: " + (mProperty.getBoolean("parking") ? " Available" : "Not Available"));
        ((TextView) view.findViewById(R.id.property_price)).setText("Price: Rs." + mProperty.getInt("price") + "/-");
        ((TextView) view.findViewById(R.id.property_price_negotiable)).setText("Price Negotiable: " + (mProperty.getBoolean("price_negotiable")? "Yes":"No"));
        ((TextView) view.findViewById(R.id.property_location)).setText("Location: " + mProperty.getString("location"));
        ((TextView) view.findViewById(R.id.property_landmark)).setText("Landmark: " + mProperty.getString("landmark"));

        ParseFile image = mProperty.getParseFile("property_image");
        final ImageView imageView = (ImageView) view.findViewById(R.id.property_full_image);
        Glide.with(getActivity()).load(image.getUrl()).centerCrop().into(imageView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
