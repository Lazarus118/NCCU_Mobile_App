package co.aulatech.nccumobileapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CredsActivity extends AppCompatActivity {
    DBHelper dbHelper;
    Button accessAcct;
    public static String user_name = "null";
    public static String pwd = "null";
    EditText u, p;
    ImageView copyU, copyP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creds);

        // Init Database
        //////////////////////////////////////////////////////////////////
        dbHelper = new DBHelper(this);
        dbHelper.insert(user_name, pwd);

        // DELETE LAST DB TABLE
//        int id_record_from_3 = 3;
//        Cursor cursor = dbHelper.getAllRecords();
//        while (cursor.moveToNext()) {
//            dbHelper.delete(id_record_from_3);
//            id_record_from_3++;
//            cursor.close();
//        }

        // EditText
        //////////////////////////////////////////////////////////////////
        u = findViewById(R.id.username);
        u.setHint(getUserFromDB());
        u.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                dbHelper.update(1, u.getText().toString(), getPWDFromDB());
                finish();
                startActivity(getIntent());
                return true;
            }
        });

        p = findViewById(R.id.password);
        p.setHint("**********");
        p.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                dbHelper.update(1, getUserFromDB(), p.getText().toString());
                finish();
                startActivity(getIntent());
                return true;
            }
        });

        // Copy
        //////////////////////////////////////////////////////////////////
        copyU = findViewById(R.id.usernameCopy);
        copyU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)  getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("USER", getUserFromDB());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(), "Username Copied!", Toast.LENGTH_LONG).show();
            }
        });

        copyP = findViewById(R.id.passwordCopy);
        copyP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)  getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("PWD", getPWDFromDB());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(), "Password Copied!", Toast.LENGTH_LONG).show();
            }
        });

        // Action button
        //////////////////////////////////////////////////////////////////
        accessAcct = findViewById(R.id.accessAcct);
        accessAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#8BCC96"));
                builder.setStartAnimations(getApplicationContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                builder.setShowTitle(true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                customTabsIntent.launchUrl(getApplicationContext(), Uri.parse("https://nccu.onlineaccounts.org/HBNet/App/"));
            }
        });
    }

    // GET USERNAME & PWD FROM DB
    //////////////////////////////////////////////////////////////////
    public String getUserFromDB() {
        final Cursor cursor = dbHelper.getRecord(1);
        if (cursor.moveToFirst()) {
            user_name = cursor.getString(1);
            cursor.close();
        }
        return user_name;
    }

    public String getPWDFromDB() {
        final Cursor cursor = dbHelper.getRecord(1);
        if (cursor.moveToFirst()) {
            pwd = cursor.getString(2);
            cursor.close();
        }
        return pwd;
    }
}
