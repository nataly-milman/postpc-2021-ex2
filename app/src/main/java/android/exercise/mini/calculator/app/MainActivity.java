package android.exercise.mini.calculator.app;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @VisibleForTesting
  public SimpleCalculator calculator;
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (calculator == null) {
      calculator = new SimpleCalculatorImpl();
    }

    // - find all views
    TextView plusView = findViewById(R.id.buttonPlus);
    TextView minusView = findViewById(R.id.buttonMinus);
    TextView equalView = findViewById(R.id.buttonEquals);
    View bsView = findViewById(R.id.buttonBackSpace);
    TextView clearView = findViewById(R.id.buttonClear);

    TextView[] buttons = {findViewById(R.id.button0), findViewById(R.id.button1),
            findViewById(R.id.button2), findViewById(R.id.button3), findViewById(R.id.button4),
            findViewById(R.id.button5), findViewById(R.id.button6), findViewById(R.id.button7),
            findViewById(R.id.button8), findViewById(R.id.button9)};

    // - initial update main text-view based on calculator's output
    textView = findViewById(R.id.textViewCalculatorOutput);
    textView.setText(calculator.output());

    // - set click listeners on all buttons to operate on the calculator and refresh main text-view

    plusView.setOnClickListener(v -> {
      calculator.insertPlus();
      textView.setText(calculator.output());
    });

    minusView.setOnClickListener(v -> {
      calculator.insertMinus();
      textView.setText(calculator.output());
    });

    equalView.setOnClickListener(v -> {
      calculator.insertEquals();
      textView.setText(calculator.output());
    });

    bsView.setOnClickListener(v -> {
      calculator.deleteLast();
      textView.setText(calculator.output());
    });

    clearView.setOnClickListener(v -> {
      calculator.clear();
      textView.setText(calculator.output());
    });

    for (int i = 0; i < 10; i++) {
      int finalI = i;
      buttons[i].setOnClickListener(v -> {
        calculator.insertDigit(finalI);
        textView.setText(calculator.output());
      });
    }
  }


  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable("savedState", calculator.saveState());
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    calculator.loadState(savedInstanceState.getSerializable("savedState"));
    textView.setText(calculator.output());
  }
}