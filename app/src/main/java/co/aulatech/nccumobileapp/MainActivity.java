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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    Button accessAcct, accessCreds;
    private InterstitialAd mInterstitialAd;

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
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        // ADMOB INIT
        //////////////////////////////////////////////////////////////////
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
                    "Download the `NCCU Mobile App` on Google play >>>\n" +
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
