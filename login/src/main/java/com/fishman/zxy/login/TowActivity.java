package com.fishman.zxy.login;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fishman.zxy.annotion.BindPath;

@BindPath("tow/tow")
public class TowActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
