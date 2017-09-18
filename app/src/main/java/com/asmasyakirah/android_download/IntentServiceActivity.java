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

    ArrayList<String> processName = new ArrayList<String>();
    ArrayList<Integer> processStatus  = new ArrayList<Integer>();
    int processIndex;
    int processPending;
    int processRunning;
    int processComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
    }

    public void startDownload(View view)
    {
        inputEditText = (EditText) findViewById(R.id.inputEditText);
        outputTextView = (TextView) findViewById(R.id.outputTextView);
        outputLayout = (LinearLayout) findViewById(R.id.outputLayout);

        // Add new download
        String msgIn = inputEditText.getText().toString();
        processName.add(msgIn);
        processStatus.add(MyIntentService.STATUS_PENDING);

        // Reset input and output UI
        inputEditText.setText("");
        outputLayout.setVisibility(View.VISIBLE);

        // Count new download
        updateOutput();

        // Starting Intent Service
        mReceiver = new MyIntentServiceReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, MyIntentService.class);
        intent.putExtra(MyIntentService.PARAM_MSG_INDEX, processIndex);
        intent.putExtra(MyIntentService.PARAM_MSG_IN, msgIn);
        intent.putExtra(MyIntentService.PARAM_RECEIVER, mReceiver);
        //intent.putExtra("requestId", 101);
        startService(intent);
        Log.e(TAG, "started MyIntentService");
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData)
    {
        int msgIndex = resultData.getInt(MyIntentService.PARAM_MSG_INDEX);
        String msgOut = resultData.getString(MyIntentService.PARAM_MSG_OUT);
        //outputTextView.setText(msgOut);

        switch (resultCode)
        {
            case MyIntentService.STATUS_RUNNING:
                processStatus.set(msgIndex, MyIntentService.STATUS_RUNNING);
                break;

            case MyIntentService.STATUS_COMPLETE:
                processStatus.set(msgIndex, MyIntentService.STATUS_COMPLETE);
                break;
        }

        updateOutput();
    }

    private void updateOutput()
    {
        processPending  = 0;
        processRunning  = 0;
        processComplete = 0;
        processIndex = processName.size()-1;
        for (int loop=0; loop<processName.size(); loop++)
        {
            switch (processStatus.get(loop))
            {
                case MyIntentService.STATUS_PENDING:
                    processPending++;
                    break;
                case MyIntentService.STATUS_RUNNING:
                    processRunning++;
                    break;
                case MyIntentService.STATUS_COMPLETE:
                    processComplete++;
                    break;
            }
        }
        if (processStatus.contains(MyIntentService.STATUS_RUNNING))
        {
            outputTextView.setText(
                    "Downloading " + processName.get(0)
                    + "(" + (processStatus.indexOf(MyIntentService.STATUS_RUNNING)+1) + "/" + processName.size() + ")");
            outputTextView.append(processName.get(0));
        }
        else
        {
            outputTextView.setText(processComplete + " downloads completed");
        }
    }
}
