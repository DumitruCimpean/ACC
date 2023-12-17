package com.example.acc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String prefsName = "MyPrefs"; // Name for the preferences file
    public static final String RAcorrect = "RandomAnimeCorrect";
    public static final String RAdrawn = "RandomAnimeDrawn";
    public static final String RAskip = "RandomAnimeSkipped";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyGlobals gob = new MyGlobals(this);

        Button randomAnime = findViewById(R.id.ranAnimOpenBtn);
        randomAnime.setOnClickListener(v -> gob.openActivity(RandomAnime.class));

    }
}