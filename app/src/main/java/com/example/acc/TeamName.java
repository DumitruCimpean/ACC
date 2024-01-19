package com.example.acc;

import static com.example.acc.MainActivity.prefsName;
import static com.example.acc.MainActivity.teamNameKey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class TeamName extends AppCompatActivity {
    private String userInput;
    private MyGlobals gob;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_name);

        pref = getSharedPreferences(prefsName, MODE_PRIVATE);
        editor = pref.edit();
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
                    addTeamToList(userInput);
                    gob.openActivity(RandomAnime.class);
                    finish();
                    handled = true;
                }
                return handled;
            }
        });

    }

    private void addTeamToList(String input) {
        editor.putString(teamNameKey, input);
        editor.apply();
    }
}
