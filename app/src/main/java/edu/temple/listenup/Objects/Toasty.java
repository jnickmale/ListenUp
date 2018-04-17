package edu.temple.listenup.Objects;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;

import edu.temple.listenup.R;

/**
 * Created by guillermo on 4/17/18.
 */

public class Toasty extends Dialog {
    Activity activity;
    Dialog dialog;
    Button add;
    Button cancel;

    public Toasty(Activity activity){
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("edit settings");
        setContentView(R.layout.fragment_user_settings);
    }
}
