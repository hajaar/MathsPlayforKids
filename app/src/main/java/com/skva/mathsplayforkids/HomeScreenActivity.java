package com.skva.mathsplayforkids;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class HomeScreenActivity extends Activity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onResume() {
        super.onResume();
        refreshName();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        refreshName();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        final Intent operatorsintent = new Intent(this, MainActivity.class);
        final Intent patternsintent = new Intent(this, MainActivity.class);
        final Intent ordersintent = new Intent(this, MainActivity.class);
        final Intent settingsintent = new Intent(this, SettingsActivity.class);
        final Button button1 = (Button) findViewById(R.id.new_operator_game);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Operator Game");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Operator Game");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Operator Game");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                operatorsintent.putExtra("TypeofGame", 0);
                startActivity(operatorsintent);
            }
        });
        final Button button2 = (Button) findViewById(R.id.settings);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Settings");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Settings");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Settings");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                startActivity(settingsintent);
            }
        });
        final Button button3 = (Button) findViewById(R.id.exit_game);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Exit");
                finish();
                moveTaskToBack(true);
            }
        });

        final Button button4 = (Button) findViewById(R.id.action_feedback);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Feedback");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Feedback");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Feedback");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "kartik.narayanan@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Maths Play for Kids");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        final Button button5 = (Button) findViewById(R.id.new_patterns_game);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Patterns Game");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Patterns Game");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Patterns Game");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                patternsintent.putExtra("TypeofGame", 1);
                startActivity(patternsintent);
            }
        });
        final Button button6 = (Button) findViewById(R.id.new_order_games);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Orders Game");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Orders Game");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Orders Game");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                ordersintent.putExtra("TypeofGame", 2);
                startActivity(ordersintent);
            }
        });
    }

    private void refreshName() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        ((TextView) findViewById(R.id.child_name)).setText("Hi " + settings.getString("pref_name", ""));
    }
}
