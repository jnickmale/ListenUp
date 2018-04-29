package edu.temple.listenup.Objects;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import edu.temple.listenup.Models.User;
import edu.temple.listenup.R;

public class PartnerProfile extends Dialog implements DialogInterface.OnClickListener {
    Context context;
    User user;

    public PartnerProfile(Context context, User user){
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

        if (user.getUserImage() != null) {
            Picasso.with(context).load(user.getUserImage()).resize(300, 300).into(profilePic);
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.wtf("PartnerProfile", which + "");
    }
}
