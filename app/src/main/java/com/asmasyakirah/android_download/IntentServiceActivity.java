package com.asmasyakirah.android_download;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/*
* References:
* 1) https://code.tutsplus.com/tutorials/android-fundamentals-intentservice-basics--mobile-6183
* 2) http://stacktips.com/tutorials/android/creating-a-background-service-in-android
* 3) https://github.com/StackTipsLab/Android-IntentService-Example*
*/

public class IntentServiceActivity extends AppCompatActivity implements MyIntentServiceReceiver.Receiver
{
    protected static final String TAG  = "IntentServiceActivity";

    MyIntentServiceReceiver mReceiver;

    EditText inputEditText;
    TextView outputTextView;
    LinearLayout outputLayout;

    Process myProcess;
    int processIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        myProcess = new Process();
        Log.e(TAG, "onCreate " + TAG);
    }

    public void startDownload(View view)
    {
        inputEditText = (EditText) findViewById(R.id.inputEditText);
        outputTextView = (TextView) findViewById(R.id.outputTextView);
        outputLayout = (LinearLayout) findViewById(R.id.outputLayout);

        // Add new download
        String msgIn = inputEditText.getText().toString();
        processIndex = myProcess.add(msgIn);
        Log.e(TAG, processIndex + "," + msgIn + "," + myProcess.getName(processIndex));

        // Starting Intent Service
        mReceiver = new MyIntentServiceReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, MyIntentService.class);
        intent.putExtra(MyIntentService.PARAM_MSG_INDEX, processIndex);
        intent.putExtra(MyIntentService.PARAM_MSG_IN, msgIn);
        intent.putExtra(MyIntentService.PARAM_RECEIVER, mReceiver);
        startService(intent);

        // Reset input and output UI
        inputEditText.setText("");
        outputLayout.setVisibility(View.VISIBLE);
        updateOutput();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData)
    {
        String msgOut = resultData.getString(MyIntentService.PARAM_MSG_OUT);
        int msgIndex = resultData.getInt(MyIntentService.PARAM_MSG_INDEX);

        switch (resultCode)
        {
            case MyIntentService.STATUS_RUNNING:
                myProcess.start(msgIndex);
                break;

            case MyIntentService.STATUS_COMPLETE:
                myProcess.end(msgIndex);
                break;
        }

        updateOutput();
    }

    private void updateOutput()
    {
        outputTextView.setText(myProcess.getProcessSummary());
    }
}
