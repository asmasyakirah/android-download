package com.asmasyakirah.android_download;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToDownload(View view)
    {
        // Get button tag
        int tag = Integer.parseInt(view.getTag().toString());

        switch (tag)
        {
            case 0:
                // Intent Service
                intent = new Intent(this, IntentServiceActivity.class);
                break;
        }

        startActivity(intent);
    }
}
