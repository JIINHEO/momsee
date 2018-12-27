package com.example.jiinheo.momsee;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(1500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,mainloginActivity.class));
        finish();
    }
}
