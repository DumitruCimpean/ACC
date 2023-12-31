package com.example.acc;

import static com.example.acc.MainActivity.RAcorrect;
import static com.example.acc.MainActivity.RAdrawn;
import static com.example.acc.MainActivity.RAskip;
import static com.example.acc.MainActivity.prefsName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RAscore extends AppCompatActivity {
    TextView guessedCount;
    TextView drawnCount;
    TextView skippedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rascore);
        SharedPreferences prefs = getSharedPreferences(prefsName, MODE_PRIVATE);

        int correct = prefs.getInt(RAcorrect, 0);
        int drawn = prefs.getInt(RAdrawn, 0);
        int skip = prefs.getInt(RAskip, 0);

        guessedCount = findViewById(R.id.guessedCount);
        drawnCount = findViewById(R.id.drawnCount);
        skippedCount = findViewById(R.id.skippedCount);
        Button nextMatch = findViewById(R.id.nextMatch);

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
            nextMatch.setOnClickListener(v -> finish());
        }

    }
}