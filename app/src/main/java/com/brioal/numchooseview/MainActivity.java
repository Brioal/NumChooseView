package com.brioal.numchooseview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brioal.numchoose.NumChooseView;

public class MainActivity extends AppCompatActivity {
    NumChooseView mNumChooseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumChooseView = findViewById(R.id.main_countChoose);
        mNumChooseView.setNum(10)
                .setMax(100)
                .setOnNumChangeListener(new NumChooseView.OnNumChangeListener() {
                    @Override
                    public void changed(int num) {

                    }
                }).init();
    }
}
