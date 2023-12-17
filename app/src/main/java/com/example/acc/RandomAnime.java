package com.example.acc;

import static com.example.acc.MainActivity.RAcorrect;
import static com.example.acc.MainActivity.RAdrawn;
import static com.example.acc.MainActivity.RAskip;
import static com.example.acc.MainActivity.prefsName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class RandomAnime extends AppCompatActivity {

    private Button processBtn;
    private Button ultimateBtn;
    private ImageButton editBtn;
    private ImageButton endBtn;
    private EditText inputBox;
    private TextView animeChosen;
    private TextView scoreText;
    private TextView orText;
    private Button rerollBtn;
    private Button startBtn;
    private ImageButton correct;
    private ImageButton wrong;
    private ArrayList<String> userList;
    private ArrayList<String> ultimateList;
    private ArrayList<String> usedList;
    private String[] animeItems;
    private String userInput;
    final int[] i = {0};
    private InputMethodManager imm;

    private boolean isRunning = false;
    private boolean isPaused = false;
    private long startTime = 0;
    private long pausedTime = 0;
    //                               v---- 5 minutes
    private long initialTimeMillis = 5  * 60 * 1000; // Initial time in milliseconds (5 minutes)
    private int guessedCount;
    private int skipCount;
    private int drawnCount;
    private Handler handler;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private MyGlobals gob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_anime);

        prefs = getSharedPreferences(prefsName, MODE_PRIVATE);
        editor = prefs.edit();
        gob = new MyGlobals(this);

        processBtn = findViewById(R.id.processBtn);
        ultimateBtn = findViewById(R.id.ultimateBtn);
        editBtn = findViewById(R.id.editBtn);
        endBtn = findViewById(R.id.endBtn);
        inputBox = findViewById(R.id.aniListInput);
        animeChosen = findViewById(R.id.animeChosen);
        scoreText = findViewById(R.id.scoreText);
        orText = findViewById(R.id.orText);
        rerollBtn = findViewById(R.id.reroll);
        startBtn = findViewById(R.id.timerStart);
        correct = findViewById(R.id.correct);
        wrong = findViewById(R.id.wrong);
        userList = new ArrayList<>();
        ultimateList = new ArrayList<>();
        usedList = new ArrayList<>();
        handler = new Handler();
        guessedCount = 0;
        skipCount = 0;
        drawnCount = 0;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editor.putInt(RAcorrect, 0);
        editor.putInt(RAskip, 0);
        editor.putInt(RAdrawn, 0);
        editor.apply();


        addExcelToList();
        processBtn.setOnClickListener(v -> {

            userList.clear();
            animeItems = null;
            userInput = inputBox.getText().toString();
            animeItems = userInput.split("\\n");
            Collections.addAll(userList, animeItems);
            reshuffle(userList);

            usedList.clear();
            usedList.addAll(userList);

            hideEditMenu();
            resetValues();

        });

        editBtn.setOnClickListener(v -> {
            showEditMenu();
        });

        endBtn.setOnClickListener(v -> {
            gob.openActivity(RAscore.class);
            showEditMenu();
            endBtn.setVisibility(View.GONE);
        });

        rerollBtn.setOnClickListener(v -> {
            reroll(usedList);
        });

        startBtn.setOnClickListener(v -> {
            showScorePicker();
            drawnCount++;
            editor.putInt(RAdrawn, drawnCount);
            editor.apply();
        });

        correct.setOnClickListener(v -> {
           hideScorePicker();
            guessedCount++;
            editor.putInt(RAcorrect, guessedCount);
            editor.apply();
            scoreText.setText("Score: " + guessedCount);
            reroll(usedList);
        });

        wrong.setOnClickListener(v -> {
            hideScorePicker();
            reroll(usedList);
        });

        ultimateBtn.setOnClickListener(v -> {
            reshuffle(ultimateList);
            usedList.clear();
            usedList.addAll(ultimateList);

            hideEditMenu();
            resetValues();
        });
    }

    private void reroll(ArrayList<String> theList){
        pauseStopwatch();
        if (theList.size() >= 1 && i[0] > 0){
            i[0]--;
            animeChosen.setText(theList.get(i[0]));
        } else if (i[0] == 0){
            reshuffle(theList);
        }
        skipCount++;
        editor.putInt(RAskip, skipCount);
        editor.apply();
    }
    private void reshuffle(ArrayList<String> theList) {
        // shuffle the list
        Collections.shuffle(theList);
        i[0] = theList.size();
    }

    private void addExcelToList() {
        try {
            // Get the AssetManager
            AssetManager assetManager = getAssets();

            InputStream inputStream = assetManager.open("anilist.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);

            for (int row = 0; row < sheet.getRows(); row++) {
                String cellContent = sheet.getCell(0, row).getContents();
                ultimateList.add(cellContent);
            }

            for (String data : ultimateList) {
                System.out.println(data);
            }

            // Close the workbook and InputStream to release resources
            workbook.close();
            inputStream.close();

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    private void hideEditMenu(){
        // hide keyboard
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        // hide ui
        inputBox.setVisibility(View.GONE);
        processBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.VISIBLE);
        endBtn.setVisibility(View.VISIBLE);
        rerollBtn.setVisibility(View.VISIBLE);
        startBtn.setVisibility(View.VISIBLE);
        animeChosen.setVisibility(View.VISIBLE);
        scoreText.setVisibility(View.VISIBLE);
        ultimateBtn.setVisibility(View.GONE);
        orText.setVisibility(View.GONE);
    }

    private void showEditMenu(){
        inputBox.setVisibility(View.VISIBLE);
        processBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.GONE);
        rerollBtn.setVisibility(View.GONE);
        startBtn.setVisibility(View.GONE);
        animeChosen.setVisibility(View.GONE);
        scoreText.setVisibility(View.GONE);
        ultimateBtn.setVisibility(View.VISIBLE);
        orText.setVisibility(View.VISIBLE);
        correct.setVisibility(View.GONE);
        wrong.setVisibility(View.GONE);
        pauseStopwatch();
        inputBox.requestFocus();
        imm.showSoftInput(inputBox, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideScorePicker(){
        correct.setVisibility(View.GONE);
        wrong.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);
        rerollBtn.setVisibility(View.VISIBLE);
    }

    private void showScorePicker(){
        correct.setVisibility(View.VISIBLE);
        wrong.setVisibility(View.VISIBLE);
        startBtn.setVisibility(View.GONE);
        rerollBtn.setVisibility(View.GONE);
    }

    private void resetValues(){
        guessedCount = drawnCount = skipCount = 0;
        editor.putInt(RAcorrect, 0);
        editor.putInt(RAdrawn, 0);
        editor.putInt(RAskip, 0);
        editor.apply();
        scoreText.setText("Score: " + guessedCount);
    }

    /////////////////////////////////////// UNUSED CODE FOR NOW ////////////////////////////////////

    private final Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            long elapsedMillis = currentTime - startTime;
            long remainingTimeMillis = initialTimeMillis - elapsedMillis;

            if (remainingTimeMillis <= 0) {
                // Timer reached zero or negative, stop the timer
                stopStopwatch();
            } else {
                updateTimerDisplay(remainingTimeMillis);
                handler.postDelayed(this, 1000);
            }
        }
    };

    private void startStopwatch() {
        if (!isRunning) {
            if (isPaused) {
                startTime += System.currentTimeMillis() - pausedTime;
                isPaused = false;
            } else {
                startTime = System.currentTimeMillis();
            }

            handler.postDelayed(updateTimerRunnable, 0);
            isRunning = true;
            startBtn.setEnabled(false);
            rerollBtn.setEnabled(true);
        }
    }

    private void pauseStopwatch() {
        if (isRunning) {
            handler.removeCallbacks(updateTimerRunnable);
            isRunning = false;
            isPaused = true;
            pausedTime = System.currentTimeMillis();
            startBtn.setEnabled(true);
        }
    }

    private void stopStopwatch() {
        if (isRunning) {
            handler.removeCallbacks(updateTimerRunnable);
            isRunning = false;
            startBtn.setEnabled(true);
        }
    }

    private void updateTimerDisplay(long remainingTimeMillis) {
        int seconds = (int) (remainingTimeMillis / 1000);
        int minutes = seconds / 60;

        seconds %= 60;
        minutes %= 60;

        String timeString = String.format("%02d:%02d", minutes, seconds);
        //timerText.setText(timeString);
    }


}