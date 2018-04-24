package edu.temple.listenup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillermo on 4/24/18.
 */

public class PartnerListAdapter extends RecyclerView.Adapter<PartnerListAdapter.ViewHolder> {

    //-----insert partner data into list
    //by PartnerListFragment
    //aka List<person> list = new ArrayList<Person>
    List<String> list = new ArrayList<String>();
    Context context;

    public PartnerListAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //called to create ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //bind data to ViewHolder
        String string = list.get(position);
        holder.profileName.setText(string);
        holder.score.setText(string);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView profilePic;
        public TextView profileName;
        public TextView score;


        public ViewHolder(View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.img);
            profileName = itemView.findViewById(R.id.partnerName);
            score = itemView.findViewById(R.id.partnerScore);
        }
    }

}
