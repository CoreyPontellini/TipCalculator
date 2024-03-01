package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText cost;
    TextView tipPercent, tipTotal, totalCost, tipQuality;
    SeekBar tipBar;
    int DEFAULT_TIP_PERCENT = 15; //default constant to assign for seekbar starting placement




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cost = findViewById(R.id.costEditText);
        tipPercent = findViewById(R.id.tipPercent);
        tipTotal = findViewById(R.id.tipTotal);
        totalCost = findViewById(R.id.totalTextView);
        tipBar = findViewById(R.id.tipSeekBar);
        tipQuality = findViewById(R.id.tipQuality);

        tipBar.setProgress(DEFAULT_TIP_PERCENT); //set to 15%
        updateTipQuality(DEFAULT_TIP_PERCENT); //call update method so value will match default tip percent
        tipTotal.setText(""); //set default values for tip total and total cost to empty strings
        totalCost.setText("");

        tipBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercent.setText(progress + "%");
                seekBar.setMax(30); // max of 30% tip
                calculateTip();
                updateTipQuality(progress); // call updatetipquality method to adjust textview based on the progress position

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calculateTip(); // call calculation method for tip and total into the listener for dynamic updating of values based on user entry

            }
        });
    }

    private void updateTipQuality(int progressPercent) {
        // Method to calculate position of progress bar and adjust the textview to different values and colors through conditionals

        if (progressPercent <= 10) {
            tipQuality.setText("Poor");
            tipQuality.setTextColor(this.getResources().getColor(R.color.PoorColor));
        } else if (progressPercent <= 15) {
            tipQuality.setText("Average");
            tipQuality.setTextColor(this.getResources().getColor(R.color.Average));
        } else if (progressPercent <= 20) {
            tipQuality.setText("Good");
            tipQuality.setTextColor(this.getResources().getColor(R.color.Good));
        } else if (progressPercent <= 25) {
            tipQuality.setText("Great");
            tipQuality.setTextColor(this.getResources().getColor(R.color.Great));
        } else {
            tipQuality.setText("Amazing");
            tipQuality.setTextColor(this.getResources().getColor(R.color.Amazing));
          }

    }

    private void calculateTip() {
        //Method to calculate tip total, total cost values based on the progress of the seekbar
        // Check to make sure there is a value so app won't crash for empty string when deleting
        // Set textview to nothing if this happens.

        if (cost.getText().toString().isEmpty()) {
            tipTotal.setText("");
            totalCost.setText("");
            return;
        }

        String costVar = cost.getText().toString();

        double userCost = Double.parseDouble(costVar);
        double tipPct = tipBar.getProgress();
        double tipAmt = (userCost * tipPct / 100);
        double finalTotal = (userCost + tipAmt);
        String tipTtl = String.format(Locale.CANADA, "%.2f", tipAmt);
        String result = String.format(Locale.CANADA,"%.2f", finalTotal);
        tipTotal.setText(tipTtl);
        totalCost.setText(result);
    }
}