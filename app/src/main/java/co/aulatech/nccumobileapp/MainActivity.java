package co.aulatech.nccumobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button accessAcct, accessCreds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action button
        //////////////////////////////////////////////////////////////////
        accessAcct = findViewById(R.id.accessAcct);
        accessAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Webview
                //////////////////////////////////////////////////////////////////
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#8BCC96"));
                builder.setStartAnimations(getApplicationContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                builder.setShowTitle(true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                customTabsIntent.launchUrl(getApplicationContext(), Uri.parse("https://nccu.onlineaccounts.org/HBNet/App/"));
            }
        });

        accessCreds = findViewById(R.id.accessCred);
        accessCreds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CredsActivity.class));
            }
        });
    }
}
