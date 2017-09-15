package com.asmasyakirah.android_download;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class MyIntentServiceReceiver extends ResultReceiver
{
    private Receiver mReceiver;

    public MyIntentServiceReceiver(Handler handler)
    {
        super(handler);
    }

    public void setReceiver(Receiver receiver)
    {
        mReceiver = receiver;
    }

    public interface Receiver
    {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData)
    {
        if (mReceiver != null)
        {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
