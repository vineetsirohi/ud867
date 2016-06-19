package com.udacity.gradle.builditbigger;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.vineet.jokesbackend.myApi.MyApi;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import java.io.IOException;

/**
 * Created by vineet on 18-Jun-16.
 */
class EndPointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    public interface AsyncTaskCallback{

        void callback(String result);
    }

    private static MyApi myApiService = null;
    private Context context;

    private AsyncTaskCallback mAsyncTaskCallback;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws
                                IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })
                    .setApplicationName("JokesProvider");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
//        Toast.makeText(context, result, Toast.LENGTH_LONG).show();


        if (mAsyncTaskCallback != null) {
            mAsyncTaskCallback.callback(result);
            mAsyncTaskCallback = null;
        }
    }

    public void setAsyncTaskCallback(AsyncTaskCallback asyncTaskCallback) {
        mAsyncTaskCallback = asyncTaskCallback;
    }
}