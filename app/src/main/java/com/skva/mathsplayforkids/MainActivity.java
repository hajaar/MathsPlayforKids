package com.skva.mathsplayforkids;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    OperatorGame operatorGame = new OperatorGame(0, 0);
    private int TypeofGame = 0;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Boolean soundtoggle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
        TypeofGame = getIntent().getExtras().getInt("TypeofGame", 0);
        int tmp_list_type = 0;
        String tmp_title = "";
        switch (TypeofGame) {
            case 0: {
                tmp_list_type = R.array.operator_game_list;
                tmp_title = getString(R.string.title_activity_main);
                break;
            }
            case 1: {
                tmp_list_type = R.array.patterns_game_list;
                tmp_title = getString(R.string.title_patterns_main);
                break;
            }
            case 2: {
                tmp_list_type = R.array.orders_game_list;
                tmp_title = getString(R.string.title_orders_main);
                break;
            }
        }
        this.setTitle(tmp_title);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        if (settings.getBoolean("pref_display", true)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        soundtoggle = settings.getBoolean("pref_sound", true);
        operatorGame.setTypeofGame(TypeofGame);
        operatorGame.setDifficult_min(0, Integer.parseInt(settings.getString("pref_gt_min", "5")));
        operatorGame.setDifficult_max(0, Integer.parseInt(settings.getString("pref_gt_max", "50")));
        operatorGame.setDifficult_min(1, Integer.parseInt(settings.getString("pref_lt_min", "5")));
        operatorGame.setDifficult_max(1, Integer.parseInt(settings.getString("pref_lt_max", "50")));
        operatorGame.setDifficult_min(4, Integer.parseInt(settings.getString("pref_between_min", "5")));
        operatorGame.setDifficult_max(4, Integer.parseInt(settings.getString("pref_between_max", "50")));
        operatorGame.setDifficult_min(2, Integer.parseInt(settings.getString("pref_add_min", "5")));
        operatorGame.setDifficult_max(2, Integer.parseInt(settings.getString("pref_add_max", "50")));
        operatorGame.setDifficult_min(3, Integer.parseInt(settings.getString("pref_sub_min", "5")));
        operatorGame.setDifficult_max(3, Integer.parseInt(settings.getString("pref_sub_max", "50")));
        operatorGame.setDifficult_min(5, Integer.parseInt(settings.getString("pref_next_min", "1")));
        operatorGame.setDifficult_max(5, Integer.parseInt(settings.getString("pref_next_max", "100")));
        operatorGame.setDifficult_min(6, Integer.parseInt(settings.getString("pref_before_min", "10")));
        operatorGame.setDifficult_max(6, Integer.parseInt(settings.getString("pref_before_max", "100")));
        operatorGame.setDifficult_min(7, Integer.parseInt(settings.getString("pref_before_min", "10")));
        operatorGame.setDifficult_max(7, Integer.parseInt(settings.getString("pref_before_max", "100")));
        operatorGame.setDifficult_min(8, Integer.parseInt(settings.getString("pref_before_min", "10")));
        operatorGame.setDifficult_max(8, Integer.parseInt(settings.getString("pref_before_max", "100")));
        operatorGame.setDifficult_min(9, Integer.parseInt(settings.getString("pref_before_min", "10")));
        operatorGame.setDifficult_max(9, Integer.parseInt(settings.getString("pref_before_max", "100")));
        operatorGame.setDifficult_min(10, Integer.parseInt(settings.getString("pref_before_min", "10")));
        operatorGame.setDifficult_max(10, Integer.parseInt(settings.getString("pref_before_max", "100")));
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String a = (button1.getText().toString());
                showSuccessFailure(operatorGame.validateAnswer(a));
                Log.d("BUTTON1onClick","a: "+a);
            }
        });
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String a = (button2.getText().toString());
                Log.d("BUTTON2onClick", "a: " + a);
                showSuccessFailure(operatorGame.validateAnswer(a));
            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String a = (button3.getText().toString());
                Log.d("BUTTON3onClick", "a: " + a);
                showSuccessFailure(operatorGame.validateAnswer(a));
            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String a = (button4.getText().toString());
                Log.d("BUTTON4onClick", "a: " + a);
                showSuccessFailure(operatorGame.validateAnswer(a));
            }
        });
        final Button button5 = (Button) findViewById(R.id.skip);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                operatorGame.generateQuestion();
                updateView();

                Log.d("SKIP","skip ");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Skip");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Skip");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


            }
        });


        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                tmp_list_type, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_list_item);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(this);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        operatorGame.setGame_type(parent.getSelectedItemPosition());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Game Type" + parent.getSelectedItemPosition());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "" + parent.getSelectedItemPosition());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "list");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        operatorGame.generateQuestion();
        updateView();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void updateView() {
        ((TextView) findViewById(R.id.score)).setText(" Score: " + operatorGame.getScore());
        ((TextView) findViewById(R.id.question)).setText(operatorGame.getQuestion());
        ((Button) findViewById(R.id.button1)).setText((operatorGame.getRandomizedArray(0)));
        ((Button) findViewById(R.id.button2)).setText((operatorGame.getRandomizedArray(1)));
        ((Button) findViewById(R.id.button3)).setText((operatorGame.getRandomizedArray(2)));
        ((Button) findViewById(R.id.button4)).setText((operatorGame.getRandomizedArray(3)));
    }

    private void showSuccessFailure(boolean success) {
        int sound, image, time;
        String text;
        if (success) {
            sound = R.raw.rightapplause;
            image = R.drawable.happysmiley;
            text = "You Are Right!";
            time = 1000;
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Correct Answer");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Correct");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        } else {
            sound = R.raw.wrongbuzzer;
            image = R.drawable.worriedsmiley;
            text = "Try Again";
            time = 500;
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Wrong Answer");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Wrong");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            final Button button1 = (Button) findViewById(R.id.button1);
        }
        if (soundtoggle) {
            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), sound);
            mp.start();
        }

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        ImageView toast_image = (ImageView) layout.findViewById(R.id.toast_image);
        toast_image.setImageResource(image);

        TextView toast_message = (TextView) layout.findViewById(R.id.toast_message);
        toast_message.setText(text);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, time);
        updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }



}
