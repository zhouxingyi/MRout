package com.fishman.zxy.transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fishman.zxy.annotion.BindPath;

@BindPath("trans/trans")
public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
    }
}
