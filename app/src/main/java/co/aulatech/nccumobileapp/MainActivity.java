package co.aulatech.nccumobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.ap.ApBanner;
import com.ap.ApEventsListener;
import com.ap.ApNotification;
import com.ap.ApPreparedAd;
import com.ap.ApSdk;

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

        // ADS INIT
        //////////////////////////////////////////////////////////////////
        ApSdk.init(getApplicationContext(), "1598056954399274887", "co.aulatech.nccumobileapp");
        ApNotification.start(this);
        ApSdk.enableTestMode();
        ApBanner banner = findViewById(R.id.container);
        banner.setEventsListener(new ApEventsListener() {
            @Override
            public void onLoaded(ApPreparedAd ad) {
                ad.show();
            }
            @Override
            public void onFailed(String reason) {
            }
            @Override
            public void onClicked() {
            }
            @Override
            public void onOpened() {
            }
            @Override
            public void onClosed() {
            }
            @Override
            public void onLeaveApplication() {
            }
        });
        //If you need custom banner size, you can use next method
        //banner.setSize(320, 50);
        banner.load();

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // TOP NAVIGATION
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_app) {
            String shareBody = Uri.parse(
                    "Download the `NCCU Mobile App` on the Google play store >>>\n" +
                            "http://play.google.com/store/apps/details?id=" + this.getPackageName()).toString();
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share this app with a someone:");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(sharingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
