package com.skva.mathsplayforkids;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
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

import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    final int TOTAL_GAMES = 5;
    private int score=0, total_attempts =0;
    private String question ="";
    private int game_type = 0;
    private int a=0;
    private int[] choiceArray = new int[TOTAL_GAMES];
    private Spinner spinner1;
    private String childname = "";
    private Boolean soundtoggle = true;
    private Boolean runonce = false;
    private int[] difficult_min = new int[TOTAL_GAMES];
    private int[] difficult_max = new int[TOTAL_GAMES];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        childname = settings.getString("pref_name", "");
        soundtoggle = settings.getBoolean("pref_sound", true);
        difficult_min[0] = Integer.parseInt(settings.getString("pref_gt_min", "5"));
        difficult_max[0] = Integer.parseInt(settings.getString("pref_gt_max", "50"));
        difficult_min[1] = Integer.parseInt(settings.getString("pref_lt_min", "5"));
        difficult_max[1] = Integer.parseInt(settings.getString("pref_lt_max", "50"));
        difficult_min[2] = Integer.parseInt(settings.getString("pref_between_min", "5"));
        difficult_max[2] = Integer.parseInt(settings.getString("pref_between_max", "50"));
        difficult_min[3] = Integer.parseInt(settings.getString("pref_add_min", "5"));
        difficult_max[3] = Integer.parseInt(settings.getString("pref_add_max", "50"));
        difficult_min[4] = Integer.parseInt(settings.getString("pref_sub_min", "5"));
        difficult_max[4] = Integer.parseInt(settings.getString("pref_sub_max", "50"));


        ((TextView) findViewById(R.id.child_name)).setText("Hi " + childname);

        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                a = Integer.parseInt(button1.getText().toString());
                validateAnswer();
                Log.d("BUTTON1onClick","a: "+a);
            }
        });
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                a = Integer.parseInt(button2.getText().toString());
                Log.d("BUTTON2onClick","a: "+a);
                validateAnswer();
            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                a = Integer.parseInt(button3.getText().toString());
                Log.d("BUTTON3onClick","a: "+a);
                validateAnswer();
            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                a = Integer.parseInt(button4.getText().toString());
                Log.d("BUTTON4onClick","a: "+a);
                validateAnswer();
            }
        });
        final Button button5 = (Button) findViewById(R.id.skip);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                total_attempts += 1;
                generateQuestion();

                Log.d("SKIP","skip ");

            }
        });
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_list, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_list_item);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(this);
        runonce = settings.getBoolean("RUN_ONCE", true);
        if (runonce) {
            onCoachMark();
            SharedPreferences.Editor editor;
            editor = settings.edit();
            editor.putBoolean("RUN_ONCE", false);
            editor.commit();
        }

        updateScore(0);
        generateQuestion();

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        game_type = parent.getSelectedItemPosition();
        if (game_type == 5) {
            game_type = 100;
        }
        Log.d("spinner ",""+game_type);
        generateQuestion();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void updateScore(int tmpScore) {
        Log.d("updateScore", " before : "+score);
        score += tmpScore;
        Log.d("updateScore", "after : "+score);
    }

    private void generateQuestion() {
        Random r = new Random();
        Log.d("generateQuestion", "" + game_type);
        if (game_type != 100) {
            generateQuestionBasedOnGameType(game_type);
        } else

        {
            generateQuestionBasedOnGameType(r.nextInt(5));
        }

        updateView();


    }


    private void validateAnswer() {
        boolean success = false;
        total_attempts += 1;
        if (a==choiceArray[3]) {
            Log.d("validateAnswer","validation failed");
            success=true;
            showSucessFailure(success);
            updateScore(1);
            generateQuestion();
        } else {
            Log.d("validateAnswer","validation failed");
            success = false;
            showSucessFailure(success);
        }
    }

    private void updateView() {
        int tempArray[] = new int[4];
        int i = 0;
        for (i=0; i<=3;i++ ) {
            int j = (int)Math.floor(Math.random() * (i + 1));
            if (j!=i) {
                tempArray[i] = tempArray[j];
            }
            tempArray[j] = choiceArray[i];
        }
        ((TextView) findViewById(R.id.score)).setText(" Score: " + score);
        ((TextView) findViewById(R.id.question)).setText(question);
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

        } else {
            sound = R.raw.wrongbuzzer;
            image = R.drawable.worriedsmiley;
            text = "Try Again";
            time = 500;
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

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

                break;
            case R.id.action_help:
                onCoachMark();
                break;
            case R.id.action_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","kartik.narayanan@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Maths Play for Kids");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

        }

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
    }

    private void generateQuestionBasedOnGameType(int tmpgame_type) {
        Random r = new Random();
        switch (tmpgame_type) {
            case 0: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int q = r.nextInt(difficulty_max - difficulty_min) + difficulty_min + 1;
                if (q == difficulty_max) {
                    q = q - 2;
                }
                Log.d("generateQuestion", "q : " + q);
                question = " >  " + q + " ? ";
                choiceArray[0] = r.nextInt(q - difficulty_min) + difficulty_min;
                choiceArray[1] = r.nextInt(q - difficulty_min) + difficulty_min;
                choiceArray[2] = r.nextInt(q - difficulty_min) + difficulty_min;
                choiceArray[3] = r.nextInt(difficulty_max - q) + q + 1;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 1: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int q = r.nextInt(difficulty_max - difficulty_min) + difficulty_min + 1;
                if (q == difficulty_max) {
                    q = q - 2;
                }
                Log.d("generateQuestion", "q : " + q);
                question = " < " + q + " ? ";
                choiceArray[0] = r.nextInt(difficulty_max - q) + q + 1;
                choiceArray[1] = r.nextInt(difficulty_max - q) + q + 1;
                choiceArray[2] = r.nextInt(difficulty_max - q) + q + 1;
                choiceArray[3] = r.nextInt(q - difficulty_min) + difficulty_min;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 2: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int q = r.nextInt(difficulty_max - difficulty_min) + difficulty_min + 1;
                if (q == difficulty_max) {
                    q = q - 2;
                }
                int p = q + 2;
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = " " + q + " ___ " + p + " ?";
                choiceArray[0] = r.nextInt(p) + difficulty_min;
                choiceArray[1] = r.nextInt(q) + difficulty_min;
                choiceArray[2] = r.nextInt(q) + p;
                choiceArray[3] = q + 1;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 3: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int mid = (difficulty_min + difficulty_max) / 2;
                int p = r.nextInt(mid - difficulty_min) + difficulty_min;
                int q = r.nextInt(difficulty_max - mid) + mid;
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = p + " + " + q + " ?";
                choiceArray[0] = p + q + r.nextInt(difficulty_max - difficulty_min) + 1;
                choiceArray[1] = q - p + r.nextInt(difficulty_max) - 1;
                choiceArray[2] = (p + q) / 2;
                choiceArray[3] = p + q;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 4: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int mid = (difficulty_min + difficulty_max) / 2;
                int p = r.nextInt(mid - difficulty_min) + difficulty_min;
                int q = r.nextInt(difficulty_max - mid) + mid;
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = q + " - " + p + " ?";
                choiceArray[0] = q - p + r.nextInt(difficulty_max - difficulty_min) + 1;
                choiceArray[1] = p + q;
                choiceArray[2] = (q - p) / 2;
                choiceArray[3] = q - p;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
        }
    }



}
