package edu.temple.listenup.Objects;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.temple.listenup.Helpers.DatabaseHelper;
import edu.temple.listenup.Helpers.PreferencesUtils;
import edu.temple.listenup.HomeScreenActivity;
import edu.temple.listenup.MainActivity;
import edu.temple.listenup.Models.User;
import edu.temple.listenup.R;

public class PartnerProfile extends Dialog implements DialogInterface.OnClickListener {
    Context context;
    User user;

    public PartnerProfile(Context context, User user) {
        super(context);
        this.context = context;
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("edit settings");
        setContentView(R.layout.dialog_partner_profile);

        ImageView exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ImageView profilePic = findViewById(R.id.partner_profile_pic);

        List<String> partnerArtists = DatabaseHelper.getPartnerArtists(user.getID(), getContext());
        List<String> userArtists = DatabaseHelper.getUserArtists(PreferencesUtils.getMySpotifyUserID(getContext()), getContext());
        List<String> sharedArtists = new ArrayList<>();
        List<String> sharedPics;

        String value = "";
        for (String artist : partnerArtists) {
            for (String userArtist : userArtists) {
                if (userArtist.equals(artist)) {
                    sharedArtists.add(artist);
                    value = value + artist + "\n";
                    System.out.println(artist);
                    Log.wtf("PartnerProfile", artist);
                }
            }
        }

        sharedPics = HomeScreenActivity.getListOfArtistImages(sharedArtists);

        if (user.getUserImage() != null) {
            Picasso.with(context).load(user.getUserImage()).resize(300, 300).into(profilePic);
        }

        TextView shared = findViewById(R.id.sharedArtists);
        shared.setText(value);

        LinearLayout layout = findViewById(R.id.dialog_linear);

        /*
        int i = 0;
        for (String value : sharedPics) {
            ImageView imageView = new ImageView(context);
            imageView.setId(i);
            imageView.setPadding(2, 2, 2, 2);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layout.addView(imageView);
            Log.wtf("ThisPicBetterWork", value);
            Picasso.with(context).load(value).resize(200, 200).centerCrop().into(imageView);
            i++;
        }
        */

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.wtf("PartnerProfile", which + "");
    }
}
