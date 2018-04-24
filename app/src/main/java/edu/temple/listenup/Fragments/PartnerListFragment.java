package edu.temple.listenup.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.temple.listenup.PartnerListAdapter;
import edu.temple.listenup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<String> list;

    public PartnerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partner_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        //connect adapter to data
        adapter = new PartnerListAdapter(list,view.getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
