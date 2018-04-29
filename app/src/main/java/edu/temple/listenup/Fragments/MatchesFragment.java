package edu.temple.listenup.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import edu.temple.listenup.Models.ImageModel;
import edu.temple.listenup.Adapters.MatchAdapter;
import edu.temple.listenup.Objects.EndlessScrollListener;
import edu.temple.listenup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    ListView matchlist;

    ArrayList<ImageModel> testlist;
    public static int[] testImages = {R.drawable.kanyewest, R.drawable.kanyewest, R.drawable.kanyewest, R.drawable.kanyewest, R.drawable.kanyewest, R.drawable.kanyewest, R.drawable.kanyewest, R.drawable.kanyewest, R.drawable.kanyewest};
    public static String[] testNameList = {"jamss", "jess", "nick", "gus", "Malik", "shawn", "karl", "pizzaman", "batman"};

    //ArrayList<HashMap<String ,String>> data = new ArrayList<HashMap<String, String>>();

    MatchAdapter matchAdapter;

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_matches, container, false);
        matchlist = v.findViewById(R.id.ListmatchesID);
    /*    matchlist.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {

                return true;
            }
        });
        */
        testlist = populateList();
        matchAdapter = new MatchAdapter(getActivity(), testlist);

        //   ArrayAdapter<String,int> lis
        // Inflate the layout for this fragment
        matchlist.setAdapter(matchAdapter);
        return v;
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
    }
    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setName(testNameList[i]);
            imageModel.setImage_drawable(testImages[i]);
            list.add(imageModel);
        }

        return list;

    }

}
