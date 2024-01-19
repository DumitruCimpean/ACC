package com.example.acc;

import static com.example.acc.MainActivity.RAcorrect;
import static com.example.acc.MainActivity.RAdrawn;
import static com.example.acc.MainActivity.RAskip;
import static com.example.acc.MainActivity.prefsName;
import static com.example.acc.MainActivity.teamNameKey;
import static com.example.acc.MainActivity.teamScoreKey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RAscore extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private TextView guessedCount;
    private TextView drawnCount;
    private TextView skippedCount;
    private MyGlobals gob;
    private int correct;
    private int drawn;
    private int skip;
    private Map<String, Integer> teamsMap;
    private String teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rascore);
        prefs = getSharedPreferences(prefsName, MODE_PRIVATE);
        editor = prefs.edit();
        gob = new MyGlobals(this);

        editor.putBoolean("raOpened", true);
        editor.apply();

        correct = prefs.getInt(RAcorrect, 0);
        drawn = prefs.getInt(RAdrawn, 0);
        skip = prefs.getInt(RAskip, 0);

        guessedCount = findViewById(R.id.guessedCount);
        drawnCount = findViewById(R.id.drawnCount);
        skippedCount = findViewById(R.id.skippedCount);
        Button nextMatch = findViewById(R.id.nextMatch);
        Button endGame = findViewById(R.id.endSession);

        if (guessedCount != null){
            guessedCount.setText(String.valueOf(correct));
        }
        if(drawnCount != null){
            drawnCount.setText(String.valueOf(drawn));
        }
        if(skippedCount != null){
            skippedCount.setText(String.valueOf(skip));
        }

        if (nextMatch != null){
            nextMatch.setOnClickListener(v -> {
                gob.openActivity(TeamName.class);
            });
        }

        teamName = prefs.getString(teamNameKey, " ");
        teamsMap = new HashMap<>();
        teamsMap.put(teamName, correct);

        endGame.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(this, Board.class);
            intent.putExtra("rankingMap", (Serializable) teamsMap);
            startActivity(intent);
            editor.putBoolean("raOpened", false);
            editor.apply();
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        correct = prefs.getInt(RAcorrect, 0);
        drawn = prefs.getInt(RAdrawn, 0);
        skip = prefs.getInt(RAskip, 0);

        if (guessedCount != null){
            guessedCount.setText(String.valueOf(correct));
        }
        if(drawnCount != null){
            drawnCount.setText(String.valueOf(drawn));
        }
        if(skippedCount != null){
            skippedCount.setText(String.valueOf(skip));
        }

        teamName = prefs.getString(teamNameKey, " ");
        teamsMap.put(teamName, correct);

    }
}