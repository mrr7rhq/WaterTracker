package fi.metropolia.simppa.watertracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3;
    Intent intent;
    int todayConsumption = 0; //For circle chart
    int todayGoal; //For circle chart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.setDailyGoalButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateChart();
    }

    public void onButton(View view) {
        if (view.getId() == b1.getId()) {
            intent = new Intent(this, UnitActivity.class);
            startActivity(intent);
        } else if (view.getId() == b2.getId()) {
            intent = new Intent(this, ShowList.class);
            startActivity(intent);
        } else if (view.getId() == b3.getId()) {
            intent = new Intent(this, DailyGoalActivity.class);
            startActivity(intent);
        }
    }

    //this function should be changed once we know daily consumption; also field and button from activity_main should be removed
    public void addBurned(View v) {
        // Get the new value from a user input and update:
        EditText burnedEditText = findViewById(R.id.burned);
        todayConsumption = Integer.parseInt(burnedEditText.getText().toString());
        updateChart();
    }

    //update circle chart
    private void updateChart() {
        // Get latest daily goal
        // 1. Open the file: get references
        SharedPreferences prefGet = getSharedPreferences("DailyGoal", Activity.MODE_PRIVATE);
        //2. Read the value, default 0 if not strored
        todayGoal = prefGet.getInt("new goal", 0);

        // Update the texts "consumed out of goal" and "XX%"
        TextView statusUpdateTextView = findViewById(R.id.statusUpdateTextView);
        TextView percentageTextView = findViewById(R.id.percentageTextView);
        statusUpdateTextView.setText(String.valueOf(todayConsumption) + " ml out of " + String.valueOf(todayGoal) + " ml");

        // Calculate the slice size and update the pie chart:
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        double d = (double) todayConsumption / (double) todayGoal;
        int progress = (int) (d * 100);
        pieChart.setProgress(progress);
        percentageTextView.setText(String.valueOf(progress) + "%");
        Log.d("TEST", String.valueOf(todayGoal));
    }

}
