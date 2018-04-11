package edu.temple.listenup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * Created by Jessica on 4/10/2018.
 */

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
