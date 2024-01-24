package com.example.acc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        LinearLayout rankLayout = findViewById(R.id.rankLayout);

        Intent intent = getIntent();
        HashMap<String, Integer> receivedRanking = (HashMap<String, Integer>) intent.getSerializableExtra("rankingMap");
        if (receivedRanking != null){
            List<Map.Entry<String, Integer>> sortedTeams = new ArrayList<>(receivedRanking.entrySet());
            sortedTeams.sort((entry1, entry2) -> {
                // Sorting in descending order.
                return entry2.getValue().compareTo(entry1.getValue());
            });

            int i = 0;
            for (Map.Entry<String, Integer> entry : sortedTeams) {
                String key = entry.getKey();
                int value = entry.getValue();
                i += 1;

                // Declarations
                RelativeLayout positionLayout = new RelativeLayout(this);
                RelativeLayout.LayoutParams positionLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                positionLayoutParams.setMargins(30, 5, 30, 5);
                positionLayout.setLayoutParams(positionLayoutParams);

                TextView nameItem = new TextView(this);
                TextView scoreItem = new TextView(this);
                nameItem.setText(i + ". " + key);
                scoreItem.setText(String.valueOf(value));

                // Styling and formatting
                nameItem.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));
                nameItem.setTextColor(Color.parseColor("#FFFFFF"));
                nameItem.setTextSize(30);
                nameItem.setSingleLine();
                nameItem.setEllipsize(TextUtils.TruncateAt.END);

                scoreItem.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));
                scoreItem.setTextColor(Color.parseColor("#FFFFFF"));
                scoreItem.setTextSize(30);

                // Params to arrange the name and score in the position layout
                RelativeLayout.LayoutParams nameLayoutParams = (RelativeLayout.LayoutParams) nameItem.getLayoutParams();
                nameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                nameLayoutParams.setMarginEnd(60);
                nameItem.setLayoutParams(nameLayoutParams);
                RelativeLayout.LayoutParams scoreLayoutParams = (RelativeLayout.LayoutParams) scoreItem.getLayoutParams();
                scoreLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                scoreItem.setLayoutParams(scoreLayoutParams);

                positionLayout.addView(nameItem);
                positionLayout.addView(scoreItem);
                rankLayout.addView(positionLayout);
            }
        }

    }
}