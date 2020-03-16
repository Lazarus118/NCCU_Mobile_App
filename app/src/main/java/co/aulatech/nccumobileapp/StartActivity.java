package co.aulatech.nccumobileapp;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // New Handler to start the Menu-Activity
        // and close this SplashActivity-Screen after some seconds.
        //////////////////////////////////////////////////////////////////
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
