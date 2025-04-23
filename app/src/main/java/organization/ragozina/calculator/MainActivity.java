package organization.ragozina.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private String currentNumber = "";
    private double firstNumber = 0;
    private String operation = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textView2);
        setupNumberButtons();
        setupOperationButtons();
        setupSpecialButtons();
    }

    private void setupNumberButtons() {
        int[] numberIds = {
                R.id.button5, R.id.button6, R.id.button7,  // 7,8,9
                R.id.button9, R.id.button10, R.id.button11, // 4,5,6
                R.id.button13, R.id.button14, R.id.button15,// 1,2,3
                R.id.button17, R.id.button18                // 0, .
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                String number = button.getText().toString();

                if (isNewOperation) {
                    currentNumber = "";
                    isNewOperation = false;
                }

                if (number.equals(".")) {
                    if (!currentNumber.contains(".")) {
                        currentNumber += ".";
                    }
                } else{
                    currentNumber += number;
                }

                updateDisplay();
            });
        }
    }

    private void setupOperationButtons() {
        int[] operationIds = {
                R.id.button4,   // *
                R.id.button3,   // /
                R.id.button12,  // +
                R.id.button16   // -
        };

        for (int id : operationIds) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                if (!currentNumber.isEmpty()) {
                    firstNumber = Double.parseDouble(currentNumber);
                    operation = button.getText().toString();
                    currentNumber = "";
                    updateDisplay();
                }
            });
        }
    }

    private void setupSpecialButtons() {
        // AC Button
        findViewById(R.id.button1).setOnClickListener(v -> {
            currentNumber = "";
            firstNumber = 0;
            operation = "";
            isNewOperation = true;
            updateDisplay();
        });

        // +/- Button
        findViewById(R.id.button2).setOnClickListener(v -> {
            if (!currentNumber.isEmpty()) {
                double value = Double.parseDouble(currentNumber);
                value *= -1;
                currentNumber = String.valueOf(value);
                updateDisplay();
            }
        });

        // = Button
        findViewById(R.id.button19).setOnClickListener(v -> {
            if (!currentNumber.isEmpty() && !operation.isEmpty()) {
                performOperation();
                operation = "";
                isNewOperation = true;
            }
        });

        // % Button
        findViewById(R.id.button8).setOnClickListener(v -> {
            if (!currentNumber.isEmpty()) {
                double value = Double.parseDouble(currentNumber);
                value /= 100;
                currentNumber = String.valueOf(value);
                updateDisplay();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void performOperation() {
        double secondNumber = Double.parseDouble(currentNumber);
        double result = 0;

        switch (operation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }

        // Удаление .0 если число целое
        currentNumber = result % 1 == 0 ?
                String.valueOf((int) result) :
                String.valueOf(result);

        updateDisplay();
    }

    private void updateDisplay() {
        if (!currentNumber.isEmpty()) {
            display.setText(currentNumber);
        } else {
            display.setText("0");
        }
    }
}