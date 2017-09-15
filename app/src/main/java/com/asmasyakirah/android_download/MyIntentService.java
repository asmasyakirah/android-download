package com.asmasyakirah.android_download;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;

public class MyIntentService extends IntentService
{
    protected static final String TAG             = "MyIntentService";
    protected static final String PARAM_MSG_INDEX = "MESSAGE_INDEX";
    protected static final String PARAM_MSG_IN    = "MESSAGE_IN";
    protected static final String PARAM_MSG_OUT   = "MESSAGE_OUT";
    protected static final String PARAM_RECEIVER  = "RECEIVER";

    protected static final int STATUS_PENDING     = 0;
    protected static final int STATUS_RUNNING     = 1;
    protected static final int STATUS_COMPLETE    = 2;

    public MyIntentService()
    {
        super(MyIntentService.class.getName());
        Log.e(TAG, "super MyIntentService");
    }
    
    @Override
    protected void onHandleIntent(Intent intent)
    {
        final ResultReceiver receiver = intent.getParcelableExtra(PARAM_RECEIVER);
        Bundle bundle = new Bundle();

        int msgIndex = intent.getIntExtra(PARAM_MSG_INDEX, -1);
        String msgIn = intent.getStringExtra(PARAM_MSG_IN);
        String msgOut = msgIn + "";
        Log.e(TAG, msgOut);

        // Download running. Return message to main thread using ResultReceiver.
        bundle.putInt(PARAM_MSG_INDEX, msgIndex);
        bundle.putString(PARAM_MSG_OUT, msgOut);
        receiver.send(STATUS_RUNNING, bundle);

        // Sleep for 5 seconds
        SystemClock.sleep(5000);

        // Download completed. Return message to main thread using ResultReceiver.
        bundle.putInt(PARAM_MSG_INDEX, msgIndex);
        bundle.putString(PARAM_MSG_OUT, msgOut);
        receiver.send(STATUS_COMPLETE, bundle);

        //this.stopSelf();
    }
}
