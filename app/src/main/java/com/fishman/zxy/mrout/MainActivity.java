package com.fishman.zxy.mrout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.fishman.zxy.annotion.BindPath;
import com.fishman.zxy.marouter.ARouter;

@BindPath("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }

    public void GoToActivity(View view) {
        ARouter.getInstance().GoToActivity("login/login",null);
    }
}
