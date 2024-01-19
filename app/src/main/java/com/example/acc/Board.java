package com.example.acc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Board extends AppCompatActivity {

    TextView teamList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        teamList = findViewById(R.id.teamLeaderboard);

        Intent intent = getIntent();
        HashMap<String, Integer> receivedRanking = (HashMap<String, Integer>) intent.getSerializableExtra("rankingMap");
        if (receivedRanking != null){
            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(receivedRanking.entrySet());
            sortedEntries.sort((entry1, entry2) -> {
                // Sorting in descending order.
                return entry2.getValue().compareTo(entry1.getValue());
            });
            for (Map.Entry<String, Integer> entry : sortedEntries) {
                String key = entry.getKey();
                int value = entry.getValue();
                teamList.setText(key + " " + value);
                // TODO: display the final leaderboard
            }
        }

    }
}