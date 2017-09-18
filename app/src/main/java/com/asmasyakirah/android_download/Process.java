package com.asmasyakirah.android_download;

import android.util.Log;

import java.util.ArrayList;

class Process
{
    private static final String TAG = "Process";

    private static final int STATUS_PENDING     = 0;
    private static final int STATUS_RUNNING     = 1;
    private static final int STATUS_COMPLETE    = 2;

    ArrayList<String> name;
    private ArrayList<Integer> status;

    int currentIndex;
    int runningIndex;
    int pendingCount;
    int runningCount;
    int completeCount;
    int totalCount;

    public Process()
    {
        this.name = new ArrayList<String>();
        this.status = new ArrayList<Integer>();
        Log.e(TAG, "new " + TAG);
    }

    public int add(String name)
    {
        this.name.add(name);
        this.status.add(STATUS_PENDING);

        this.currentIndex = getTotalCount() - 1;
        return this.currentIndex;
    }

    public void start(int currentIndex)
    {
        this.status.set(currentIndex, STATUS_RUNNING);
    }

    public void end(int currentIndex)
    {
        this.status.set(currentIndex, STATUS_COMPLETE);
    }

    public String getName(int currentIndex)
    {
        return this.name.get(currentIndex);
    }

    public int getRunningIndex()
    {
        this.runningIndex = -1;

        try
        {
            this.runningIndex = this.status.indexOf(STATUS_RUNNING);
        }
        catch (Exception ex)
        {
            // this.runningIndex = -1;
        }

        return this.runningIndex;
    }

    public int getTotalCount()
    {
        this.totalCount = this.name.size();
        return this.totalCount;
    }

    public int getPendingCount()
    {
        this.pendingCount = 0;

        for (int loop=0; loop<getTotalCount(); loop++)
        {
            if (this.status.get(loop) == STATUS_PENDING)
            {
                this.pendingCount++;
            }
        }

        return this.pendingCount;
    }

    public int getRunningCount()
    {
        this.runningCount = 0;

        for (int loop=0; loop<getTotalCount(); loop++)
        {
            if (this.status.get(loop) == STATUS_RUNNING)
            {
                this.runningCount++;
            }
        }

        return this.runningCount;
    }

    public int getCompleteCount()
    {
        this.completeCount = 0;

        for (int loop=0; loop<getTotalCount(); loop++)
        {
            if (this.status.get(loop) == STATUS_COMPLETE)
            {
                this.completeCount++;
            }
        }

        return this.completeCount;
    }

    public String getProcessSummary()
    {
        String summary = "";

        if (getRunningCount() > 0)
        {
            summary = "Downloading " + this.name.get(getRunningIndex())
                    + " (" + (getRunningIndex()+1) + "/" + getTotalCount() + ")";
        }
        else
        {
            summary = getCompleteCount() + " downloads completed.";
        }

        /*
        for (int loop=0; loop<getTotalCount(); loop++)
        {
            summary = summary + "\n" + name.get(loop) + "," + status.get(loop);
        }
        */

        return summary;
    }

    public void clearAll()
    {
        this.name.clear();
        this.status.clear();
    }
}
