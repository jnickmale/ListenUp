package edu.temple.listenup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.temple.listenup.Models.User;
import edu.temple.listenup.Objects.PartnerProfile;
import edu.temple.listenup.R;

/**
 * Created by guillermo on 4/24/18.
 */

public class PartnerListAdapter extends RecyclerView.Adapter<PartnerListAdapter.ViewHolder> {

    //-----insert partner data into list
    //by PartnerListFragment
    //aka List<person> list = new ArrayList<Person>
    List<User> list = new ArrayList<User>();
    //List<String> list = new ArrayList<>();
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
        final User user = list.get(position);
        holder.profileName.setText(user.getDisplayName());
        holder.score.setText(String.valueOf(user.getDistance()));

        String image = user.getUserImage();

        if (image != null) {
            Picasso.with(context).load(image).resize(300, 300).into(holder.profilePic);
        }

        holder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartnerProfile partnerProfile = new PartnerProfile(context, user);
                partnerProfile.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
