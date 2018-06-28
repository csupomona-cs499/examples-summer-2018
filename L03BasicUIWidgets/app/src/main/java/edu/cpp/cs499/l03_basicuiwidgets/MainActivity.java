package edu.cpp.cs499.l03_basicuiwidgets;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    TextView req1TextView;
    TextView req2TextView;
    TextView req3TextView;

    Button testButton;

    TextView switchTextView;
    Switch switchTest;

    SeekBar seekBar;
    TextView minTextView;
    TextView curTextView;
    TextView maxTextView;

    CheckBox checkBox1;
    CheckBox checkBox2;

    TextView textCheckBox1;
    TextView textCheckBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton = findViewById(R.id.testButton);
        textView = findViewById(R.id.textView);
        req1TextView = findViewById(R.id.req1TextView);
        req2TextView = findViewById(R.id.req2TextView);
        req3TextView = findViewById(R.id.req3TextView);

        switchTextView = findViewById(R.id.switchStatusTextView);
        switchTest = findViewById(R.id.switchTest);

        seekBar = findViewById(R.id.seekBar);
        minTextView = findViewById(R.id.minTextView);
        maxTextView = findViewById(R.id.maxTextView);
        curTextView = findViewById(R.id.currentTextView);

        checkBox1 = findViewById(R.id.option1Checkbox);
        checkBox2 = findViewById(R.id.option2Checkbox);
        textCheckBox1 = findViewById(R.id.checkBoxResult1);
        textCheckBox2 = findViewById(R.id.checkBoxResult2);

        testButton.setEnabled(false);

        final EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("TEST", editable.toString());
                String password = editable.toString();
                boolean isLongerThanEight = false;
                if (password.length() >= 8) {
                    req1TextView.setTextColor(Color.GREEN);
                    isLongerThanEight = true;
                } else {
                    req1TextView.setTextColor(Color.RED);
                    isLongerThanEight = false;
                }

                boolean hasUpperCase = false;
                for(char c : password.toCharArray()) {
                    if (c >= 'A' && c <= 'Z') {
                        hasUpperCase = true;
                        break;
                    }
                }
                if (hasUpperCase) {
                    req2TextView.setTextColor(Color.GREEN);
                } else {
                    req2TextView.setTextColor(Color.RED);
                }

                boolean hasNumber = false;
                for(char c : password.toCharArray()) {
                    if (c >= '0' && c <= '9') {
                        hasNumber = true;
                        break;
                    }
                }
                if (hasNumber) {
                    req3TextView.setTextColor(Color.GREEN);
                } else {
                    req3TextView.setTextColor(Color.RED);
                }

                if (isLongerThanEight && hasNumber && hasUpperCase) {
                    testButton.setEnabled(true);
                } else {
                    testButton.setEnabled(false);
                }
            }
        });


        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText().toString());
            }
        });

        switchTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchTextView.setText("Switch: " + (b ? "On" : "Off"));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                curTextView.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                textCheckBox1.setText("Checkbox 1: " + (b ? "Yes" : "No"));
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                textCheckBox2.setText("Checkbox 2: " + (b ? "Yes" : "No"));
            }
        });
    }
}
