package edu.csc4360.threads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class FibonacciMainActivity extends AppCompatActivity {
    ProgressBar mProgressBar;
    EditText mNumberEditText;
    TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci_main);
        mProgressBar = findViewById(R.id.mProgressBar);
        mNumberEditText = findViewById(R.id.mNumberEditText);
        mResultTextView = findViewById(R.id.mResultTextView);
    }

    private int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public void fibonacciClick(View view) {
        // Display progress bar
        mProgressBar.setVisibility(View.VISIBLE);

        // Find the nth Fibonacci number using the given number
        int num = Integer.parseInt(mNumberEditText.getText().toString());
        int fibNumber = fibonacci(num);

        // Show the result with commas in the right place
        mResultTextView.setText("Result: " +
                NumberFormat.getNumberInstance(Locale.US).format(fibNumber));

        // Hide progress bar
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void onClickProbar(View view) {
        int v = mProgressBar.getVisibility();
        mProgressBar.setVisibility(View.VISIBLE);


    }

}