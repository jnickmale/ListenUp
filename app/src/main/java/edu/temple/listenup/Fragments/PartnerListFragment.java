package edu.temple.listenup.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.temple.listenup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerListFragment extends Fragment {

    public PartnerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partner_list, container, false);
    }

}
