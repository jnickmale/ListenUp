package edu.temple.listenup.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.temple.listenup.Models.ImageModel;
import edu.temple.listenup.R;

import edu.temple.listenup.Fragments.CollabFragment;
import kaaes.spotify.webapi.android.models.UserPublic;

/**
 * Created by kingJ on 4/23/2018.
 */

public class MatchAdapter extends BaseAdapter //ArrayAdapter<User>
{
private  Context context;
private ArrayList<Match> usersArrayList;

    public MatchAdapter(@NonNull Context context, ArrayList<Match> usersArrayList) {
       // super(context, R.layout.item_row, usersArrayList);
    this.context=context;
    this.usersArrayList = usersArrayList;
    }


    @Override
    public int getCount() {
        return usersArrayList.size();
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public Object getItem(int i) {
        return usersArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        final Match match = (Match)getItem(position);
        if(convertView==null){
            holder =new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try {
                convertView = inflater.inflate(R.layout.item_row, null, true);
            }catch (NullPointerException e){
                Log.wtf("inflater","the inflater is null");
            }

            holder.username = convertView.findViewById(R.id.namesID);

            holder.profilepicture = convertView.findViewById(R.id.profilePicture);





            Button playlistButton = convertView.findViewById(R.id.ChatID);
            playlistButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CollabFragment collabFragment = CollabFragment.getInstance(match.getUserID(), match.getUserName(), match.getWithUserID(), match.getWithUserName(), match.getMatchID());
                    FragmentManager fragmentManager = ((AppCompatActivity)v.getContext()).getFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.attachTo, collabFragment);
                }
            });



            Button chatButton = convertView.findViewById(R.id.ChatID);
            chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("username", match.getUserName());
                    intent.putExtra("matchUsername", match.getWithUserName());
                    intent.putExtra("chatID", match.getMatchID());
                    ((AppCompatActivity)v.getContext()).startActivity(intent);
                }
            });

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();

        }
        holder.username.setText(usersArrayList.get(position).getWithUserName());


        final View view = convertView;
        final String imageURL;
        ((HomeScreenActivity)convertView.getContext()).loadUserImageIntoView(match.getWithUserID(), holder.profilepicture);



        return  convertView;
    }

private  class  ViewHolder{

        protected TextView username;
        private  ImageView profilepicture;
}



}