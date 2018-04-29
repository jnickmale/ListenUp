package edu.temple.listenup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.temple.listenup.Models.ImageModel;
import edu.temple.listenup.R;

/**
 * Created by kingJ on 4/23/2018.
 */

public class MatchAdapter extends BaseAdapter //ArrayAdapter<User>
{
private  Context context;
private ArrayList<ImageModel> usersArrayList;

    public MatchAdapter(@NonNull Context context, ArrayList<ImageModel> usersArrayList) {
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
        return getCount();
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
    ViewHolder holder;
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

        convertView.setTag(holder);
        }else{
        holder = (ViewHolder)convertView.getTag();

    }
        holder.username.setText(usersArrayList.get(position).getName());
        holder.profilepicture.setImageResource(usersArrayList.get(position).getImage_drawable());


    return  convertView;
    }

private  class  ViewHolder{

        protected TextView username;
        private  ImageView profilepicture;
}

}