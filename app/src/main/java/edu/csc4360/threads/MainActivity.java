package edu.csc4360.threads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickMain(View view){
        Intent intent;
        intent = new Intent(MainActivity.this, FibonacciMainActivity.class);
        startActivity(intent);
    }

    public void onClickBackground(View view){
        Intent intent;
        intent = new Intent(MainActivity.this, FibonacciBackground.class );
        startActivity(intent);
    }

    public void onClickAsync(View view){
        Intent intent;
        intent = new Intent(MainActivity.this, AsyncActivity.class );
        startActivity(intent);
    }

}
