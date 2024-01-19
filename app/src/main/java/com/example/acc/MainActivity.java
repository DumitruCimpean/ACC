package com.example.acc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String prefsName = "MyPrefs"; // Name for the preferences file
    public static final String RAcorrect = "RandomAnimeCorrect";
    public static final String RAdrawn = "RandomAnimeDrawn";
    public static final String RAskip = "RandomAnimeSkipped";
    public static final String teamNameKey = "teamNameKey";
    public static final String teamScoreKey = "teamScoreKey";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyGlobals gob = new MyGlobals(this);
        prefs = getSharedPreferences(prefsName, MODE_PRIVATE);
        editor = prefs.edit();

        Button randomAnime = findViewById(R.id.ranAnimOpenBtn);
        randomAnime.setOnClickListener(v -> gob.openActivity(TeamName.class));

        editor.putBoolean("raOpened", false);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editor.putBoolean("raOpened", false);
        editor.apply();
    }
}