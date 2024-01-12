package com.example.acc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class TeamName extends AppCompatActivity {
    private String userInput;
    private MyGlobals gob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_name);
        gob = new MyGlobals(this);

        TextView debug = findViewById(R.id.debugText);
        EditText teamInput = findViewById(R.id.teamInput);

        teamInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    userInput = teamInput.getText().toString();
                    debug.setText(userInput);
                    // TODO: fetch the userInput and add it to an excel/array
                    gob.openActivity(RandomAnime.class);
                    handled = true;
                }
                return handled;
            }
        });

        // TODO: finish the team input and leaderboard system
    }
}
