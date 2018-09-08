package com.example.nicholaskriss.electrictime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String distResultString(double range, double dist, double speed, int round) {
        if (range < dist) {
            return "Max range is only " + Double.toString(range) + " miles!";
        } else {
            double time = dist / speed;
            if (time < 1) {
                return Double.toString((double) ((int) (time * 60 * round)) / round) + " minutes";
            } else if (time < 2) {
                return "1 hour and " +
                        Double.toString((double) ((int) ((time - Math.floor(time)) * 60 * round)) / round) +
                        " minutes";
            } else {
                return Integer.toString((int) time) + " hours and " +
                        Double.toString((double) ((int) ((time - Math.floor(time)) * 60 * round)) / round) +
                        " minutes";
            }
        }
    }

    private String timeResultString(double range, double time, double dist, int round) {
        if (range < dist) {
            return Double.toString(range) + " miles (the max range!)";
        } else {
            return Double.toString(dist) + " miles";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int round = 1000;
        final Spinner transportSpinner = findViewById(R.id.transportSpinner);
        final TextView enterText = findViewById(R.id.enterText);
        final Button calculate = findViewById(R.id.calculate);
        final TextView result = findViewById(R.id.result);
        final TextView units = findViewById(R.id.units);
        final Switch switchInput = findViewById(R.id.switchInput);
        final double[] speeds = {0.0, 18.0, 26.0, 15.0, 8.0, 22.0,
                19.0, 10.0, 12.5, 12.5, 3.1};
        final double[] ranges = {0.0, 7.0, 31.0, 8.0, 8.0, 10.0,
                7.0, 7.0, 15.0, 24.0, 30.0};
        final String[] transports = {"select transport", "Boosted Mini S Board",
            "Evolve Skateboard", "GeoBlade 500", "Hovertrax Hoverboard",
            "MotoTec Skateboard", "OneWheel", "Razor Scooter",
            "Segway Ninebot One S1", "Segway i2 SE", "Walking"};

        final TextView moreResults1 = findViewById(R.id.moreResults1);
        final TextView moreResults2 = findViewById(R.id.moreResults2);
        final TextView moreResults3 = findViewById(R.id.moreResults3);
        final TextView moreResults4 = findViewById(R.id.moreResults4);
        final TextView moreResults5 = findViewById(R.id.moreResults5);
        final TextView moreResults6 = findViewById(R.id.moreResults6);
        final TextView moreResults7 = findViewById(R.id.moreResults7);
        final TextView moreResults8 = findViewById(R.id.moreResults8);
        final TextView moreResults9 = findViewById(R.id.moreResults9);
        final TextView[] moreResults = {moreResults1, moreResults2, moreResults3,
            moreResults4, moreResults5, moreResults6, moreResults7, moreResults8,
            moreResults9};

        switchInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    enterText.setHint("Enter Distance");
                    enterText.setText("");
                    switchInput.setText("Distance to Time");
                    units.setText("miles");
                } else {
                    enterText.setHint("Enter Time");
                    enterText.setText("");
                    switchInput.setText("Time to Distance");
                    units.setText("minutes");
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    int pos = transportSpinner.getSelectedItemPosition();
                    result.setTextColor(Color.BLACK);
                    if (pos == 0) {
                        result.setText("Please select a method of transport");
                        for (int i = 0; i < 9; i++) {
                            moreResults[i].setText("");
                        }
                    } else {
                        double input = Double.valueOf(enterText.getText().toString());
                        double range = ranges[pos];
                        double speed = speeds[pos];
                        if (!switchInput.isChecked()) {
                            result.setText(distResultString(range, input, speed, round));
                            if (range < input) {
                                result.setTextColor(Color.RED);
                            }
                        } else {
                            double dist = (double) ((int) (round * input * speed / 60)) / round;
                            result.setText(timeResultString(range, input, dist, round));
                            if (range < dist) {
                                result.setTextColor(Color.BLUE);
                            }
                        }

                        for (int i = 1; i < 11; i++) {
                            if (i != pos) {
                                int j;
                                if (i < pos) {
                                    j = i - 1;
                                } else {
                                    j = i - 2;
                                }
                                String otherTransports = "";
                                moreResults[j].setTextColor(Color.BLACK);
                                otherTransports += transports[i] + ": \n";
                                range = ranges[i];
                                speed = speeds[i];
                                if (!switchInput.isChecked()) {
                                    otherTransports += distResultString(range, input, speed, round);
                                    if (range < input) {
                                        moreResults[j].setTextColor(Color.RED);
                                    }
                                } else {
                                    double dist = (double) ((int) (round * input * speed / 60)) / round;
                                    otherTransports += timeResultString(range, input, dist, round);
                                    if (range < dist) {
                                        moreResults[j].setTextColor(Color.BLUE);
                                    }
                                }
                                moreResults[j].setText(otherTransports + "\n");
                            }
                        }
                    }

                } catch (NumberFormatException e){
                    if (!switchInput.isChecked()) {
                        result.setText("Please enter a distance to travel");
                    } else {
                        result.setText("Please enter a time to travel");
                    }
                    for (int i = 0; i < 9; i++) {
                        moreResults[i].setText("");
                    }
                }

            }
        });

    }
}
