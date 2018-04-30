package edu.temple.listenup.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.temple.listenup.Adapters.PartnerListAdapter;
import edu.temple.listenup.Helpers.DatabaseHelper;
import edu.temple.listenup.Models.User;
import edu.temple.listenup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerListFragment extends Fragment implements DatabaseHelper.DatabaseUsersReceivedListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<User> userList;
    private View view;

    public PartnerListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_partner_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //------- this is where we will insert the partner list data
        DatabaseHelper.getAllUsersWithinRadius(this, getActivity());
/*
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        ///---------
*/
        //connect data to adapter
        if (userList != null) {
            adapter = new PartnerListAdapter(userList, view.getContext());
            recyclerView.setAdapter(adapter);
        }


        return view;
    }

    @Override
    public void onDataReceived(List<User> data) {
        userList = data;

        if(userList != null){
            for (User user : userList){
                List<String> list = new ArrayList<String>();
                int Rating = 0;
                list = DatabaseHelper.getPartnerArtists(user.getID(),getActivity());
                for(String artist : list){
                    
                }
            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
