package com.redheadhammer.calculator20;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.redheadhammer.calculator20.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String number;

    // firstNumber will contain overall result, lastNumber will contain new values
    private double firstNumber = 0, lastNumber = 0;

    // to remember the previous operator
    private String status = "";
    private boolean operator = false;

    // to keep the output values decimal formatted
    private DecimalFormat mFormat = new DecimalFormat("######.######");

    // boolean for status of dot
    private boolean dot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn0.setOnClickListener(this::numberClick);
        binding.btn1.setOnClickListener(this::numberClick);
        binding.btn2.setOnClickListener(this::numberClick);
        binding.btn3.setOnClickListener(this::numberClick);
        binding.btn4.setOnClickListener(this::numberClick);
        binding.btn5.setOnClickListener(this::numberClick);
        binding.btn6.setOnClickListener(this::numberClick);
        binding.btn7.setOnClickListener(this::numberClick);
        binding.btn8.setOnClickListener(this::numberClick);
        binding.btn9.setOnClickListener(this::numberClick);
        // TODO: Dot can be used only once in a number.
        binding.btnDot.setOnClickListener(this::numberClick);


        // TODO: One sign can't be followed by another.
        binding.btnPlus.setOnClickListener(this::additionClick);
        // TODO: A number can start with a minus sign
        binding.btnMinus.setOnClickListener(this::minusClick);
        binding.btnMulti.setOnClickListener(this::multiplyClick);
        binding.btnDivide.setOnClickListener(this::divideClick);

        binding.btnEqual.setOnClickListener(this::onEqualClick);
        binding.btnAC.setOnClickListener(this::onAcClick);
        binding.btnDEL.setOnClickListener(this::onDelClick);
    }


    private void numberClick(View view) {
        String btnTxt = ((Button) view).getText().toString();

        // Don't do anything if btn pressed is dot and it is already used before
        if (btnTxt.equals(".") && dot) {
            return;
        }

        // If btn pressed is dot set dot to true
        if (btnTxt.equals(".")) {
            dot = true;
        }

        if (number == null) {
            number = btnTxt;
        } else {
            number = number + btnTxt;
        }

        binding.tvResult.setText(number);
        operator = true;
    }

    private void onDelClick(View view) {
        // COMPLETED: DEL shouldn't be functional if there is no number otherwise app will crash
        if (number.length() >= 1) {
            number = number.substring(0, number.length() - 1);
            binding.tvResult.setText(number);
        }

    }

    private void onEqualClick(View view) {
        if (operator) {
            switch (status) {
                case "multiply":
                    multiply();
                    break;
                case "divide":
                    divide();
                    break;
                case "minus":
                    minus();
                    break;
                case "sum":
                    addition();
                    break;
                default:
                    firstNumber = Double.parseDouble(binding.tvResult.getText().toString());
                    break;
            }
        }

        operator = false;
        status = "";
    }

    private void additionClick(View view) {
        updateHistory("+");

        operate();
        status = "sum";
    }



    private void minusClick(View view) {
        updateHistory("-");

        operate();
        status = "minus";
    }

    private void multiplyClick(View view) {
        updateHistory("*");

        operate();
        status = "multiply";
    }

    private void divideClick(View view) {
        updateHistory("/");

        operate();
        status = "divide";
    }

    private void operate() {
        if (operator) {
            switch (status) {
                case "multiply":
                    multiply();
                    break;
                case "divide":
                    divide();
                    break;
                case "minus":
                    minus();
                    break;
                default:
                    addition();
                    break;
            }
        }

        dot = false;
        operator = false;
        number = null;
    }

    private void updateHistory(String sign) {
        String history = binding.tvHistory.getText().toString();
        String currentResult = binding.tvResult.getText().toString();
        binding.tvHistory.setText(String.format("%s%s%s", history, currentResult, sign));
    }

    private void addition() {
        lastNumber = Double.parseDouble(number);
        firstNumber = lastNumber + firstNumber;

        binding.tvResult.setText(mFormat.format(firstNumber));
    }

    private void minus() {
        if (firstNumber == 0) {
            firstNumber = Double.parseDouble(binding.tvResult.getText().toString());
        } else {
            lastNumber = Double.parseDouble(binding.tvResult.getText().toString());
            firstNumber = firstNumber - lastNumber;

            binding.tvResult.setText(mFormat.format(firstNumber));
        }
    }

    private void multiply() {
        if (firstNumber == 0) {
            firstNumber = 1;
        }
        lastNumber = Double.parseDouble(binding.tvResult.getText().toString());

        firstNumber = firstNumber * lastNumber;
        binding.tvResult.setText(mFormat.format(firstNumber));
    }

    private void divide() {
        lastNumber = Double.parseDouble(binding.tvResult.getText().toString());
        if (firstNumber == 0) {
            firstNumber = lastNumber;
        } else {
            firstNumber = firstNumber / lastNumber;
        }

        binding.tvResult.setText(mFormat.format(firstNumber));
    }


    private void onAcClick(View view) {
        number = null;
        status = "";
        dot = false;
        operator = false;
        firstNumber = 0;
        lastNumber = 0;

        binding.tvResult.setText("0");
        binding.tvHistory.setText("");
    }
}