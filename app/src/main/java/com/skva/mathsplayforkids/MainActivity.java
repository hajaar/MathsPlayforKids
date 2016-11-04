package com.skva.mathsplayforkids;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
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
    private FirebaseAnalytics mFirebaseAnalytics;
    private Boolean soundtoggle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        soundtoggle = settings.getBoolean("pref_sound", true);
        operatorGame.setDifficult_min(0, Integer.parseInt(settings.getString("pref_gt_min", "5")));
        operatorGame.setDifficult_max(0, Integer.parseInt(settings.getString("pref_gt_max", "50")));
        operatorGame.setDifficult_min(1, Integer.parseInt(settings.getString("pref_lt_min", "5")));
        operatorGame.setDifficult_max(1, Integer.parseInt(settings.getString("pref_lt_max", "50")));
        operatorGame.setDifficult_min(2, Integer.parseInt(settings.getString("pref_between_min", "5")));
        operatorGame.setDifficult_max(2, Integer.parseInt(settings.getString("pref_between_max", "50")));
        operatorGame.setDifficult_min(3, Integer.parseInt(settings.getString("pref_add_min", "5")));
        operatorGame.setDifficult_max(3, Integer.parseInt(settings.getString("pref_add_max", "50")));
        operatorGame.setDifficult_min(4, Integer.parseInt(settings.getString("pref_sub_min", "5")));
        operatorGame.setDifficult_max(4, Integer.parseInt(settings.getString("pref_sub_max", "50")));
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int a = Integer.parseInt(button1.getText().toString());
                showSucessFailure(operatorGame.validateAnswer(a));
                Log.d("BUTTON1onClick","a: "+a);
            }
        });
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int a = Integer.parseInt(button2.getText().toString());
                Log.d("BUTTON2onClick","a: "+a);
                showSucessFailure(operatorGame.validateAnswer(a));
            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int a = Integer.parseInt(button3.getText().toString());
                Log.d("BUTTON3onClick","a: "+a);
                showSucessFailure(operatorGame.validateAnswer(a));
            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int a = Integer.parseInt(button4.getText().toString());
                Log.d("BUTTON4onClick","a: "+a);
                showSucessFailure(operatorGame.validateAnswer(a));
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
                R.array.game_list, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_list_item);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(this);
        Boolean runonce = settings.getBoolean("RUN_ONCE", true);
        if (runonce) {
            onCoachMark();
            SharedPreferences.Editor editor;
            editor = settings.edit();
            editor.putBoolean("RUN_ONCE", false);
            editor.commit();
        }


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
        int tempArray[] = new int[4];
        for (int i = 0; i <= 3; i++) {
            int j = (int)Math.floor(Math.random() * (i + 1));
            if (j!=i) {
                tempArray[i] = tempArray[j];
            }
            tempArray[j] = operatorGame.getChoiceArray(i);
        }
        ((TextView) findViewById(R.id.score)).setText(" Score: " + operatorGame.getScore());
        ((TextView) findViewById(R.id.question)).setText(operatorGame.getQuestion());
        ((Button) findViewById(R.id.button1)).setText(Integer.toString(tempArray[0]));
        ((Button) findViewById(R.id.button2)).setText(Integer.toString(tempArray[1]));
        ((Button) findViewById(R.id.button3)).setText(Integer.toString(tempArray[2]));
        ((Button) findViewById(R.id.button4)).setText(Integer.toString(tempArray[3]));
    }

    private void showSucessFailure(boolean success) {
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
    public void onCoachMark(){

        final Dialog dialog = new Dialog(this, R.style.WalkthroughTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.coach_mark);
        dialog.setCanceledOnTouchOutside(true);
        //for dismissing anywhere you touch

        View masterView = dialog.findViewById(R.id.coach_mark_master_view);
        masterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Help");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Help");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }


}
