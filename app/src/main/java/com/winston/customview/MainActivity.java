package com.winston.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    CircularProgress circularProgress;
    HorizontalBar mHorizontalBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularProgress = (CircularProgress) findViewById(R.id.progress);
        mHorizontalBar = (HorizontalBar) findViewById(R.id.horizontalBar);
        Button btn = (Button) findViewById(R.id.button);
        circularProgress.setProgressValue(0.9f);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circularProgress.showAnimation();
                mHorizontalBar.showAnimation(0.9f);
            }
        });
    }
}
