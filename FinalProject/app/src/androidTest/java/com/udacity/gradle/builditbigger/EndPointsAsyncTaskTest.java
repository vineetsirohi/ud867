package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.support.v4.util.Pair;
import android.test.ActivityUnitTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by vineet on 19-Jun-16.
 */
public class EndPointsAsyncTaskTest extends ActivityUnitTestCase<MainActivity> {

    private Context mContext;

    public EndPointsAsyncTaskTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        mContext = getActivity();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public final void testAsynTask() throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);
        final String[] joke = new String[1];

        final EndPointsAsyncTask endPointsAsyncTask = new EndPointsAsyncTask();
        endPointsAsyncTask.setAsyncTaskCallback(new EndPointsAsyncTask.AsyncTaskCallback() {
            @Override
            public void callback(String result) {
                signal.countDown();
                joke[0] = result;
            }
        });

        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                endPointsAsyncTask.execute(new Pair<Context, String>(mContext, "name"));
            }
        });

    /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
        signal.await(30, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        assertTrue(joke[0] != null && joke[0].length() > 0);
    }
}


