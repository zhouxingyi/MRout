package com.fishman.zxy.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.fishman.zxy.annotion.BindPath;
import com.fishman.zxy.marouter.ARouter;

@BindPath("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void GoToActivity(View view) {
        ARouter.getInstance().GoToActivity("trans/trans",null);
    }
}
