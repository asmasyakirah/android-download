# Android-Download #

### What is this repository for? ###

This simple Android app demonstrates how I would like to do my data download one-by-one in queue without causing ANR (Application Not Responding) or interrupting my UI thread. 
Everytime I click the Start button, new download will be added to queue.

### How it is done? ###

1. **IntentService**. I use IntentService to create a Background Service. This service will do download task asynchronously.
2. **ResultReceiver**. I implemented ResultReceiver to receive update from IntentService to my UI thread. Then I can show the summary of current progress in the UI.

### Screenshots ###
![Download start](https://bytebucket.org/asmasyakirah/android-download/raw/707eb69ed4508945ccc97535ac6104ef0abd0c43/download_start.png)
![Download running](https://bytebucket.org/asmasyakirah/android-download/raw/707eb69ed4508945ccc97535ac6104ef0abd0c43/download_running.png)
![Download completed](https://bytebucket.org/asmasyakirah/android-download/raw/707eb69ed4508945ccc97535ac6104ef0abd0c43/download_completed.png)

### References ###

1. https://code.tutsplus.com/tutorials/android-fundamentals-intentservice-basics--mobile-6183
2. http://stacktips.com/tutorials/android/creating-a-background-service-in-android
3. https://github.com/StackTipsLab/Android-IntentService-Example*