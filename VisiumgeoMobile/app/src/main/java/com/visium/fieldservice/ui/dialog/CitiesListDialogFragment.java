package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class CitiesListDialogFragment extends AppCompatDialogFragment {

    private static List<City> mCities;
    private static CitiesDialogListener mListener;

    public static AppCompatDialogFragment newInstance(CitiesDialogListener listener, List<City> cities) {
        CitiesListDialogFragment.mCities = cities;
        CitiesListDialogFragment.mListener = listener;
        return new CitiesListDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        ListView citiesListView = (ListView) view.findViewById(R.id.cities_list);

        List<String> citiesName = new ArrayList<>(mCities.size());

        for (City city : mCities) {
            citiesName.add(city.getName());
        }

        citiesListView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, citiesName));
        citiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dismissAllowingStateLoss();
                mListener.onCitySelected(mCities.get(i));
            }
        });

        view.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        return view;
    }

}