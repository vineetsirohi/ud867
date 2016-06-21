package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import in.vasudev.jokeactivitylib.JokeDisplayActivity;


public class MainActivity extends ActionBarActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mProgressBar.setVisibility(View.VISIBLE);

        EndPointsAsyncTask endPointsAsyncTask = new EndPointsAsyncTask();
        endPointsAsyncTask.setAsyncTaskCallback(new EndPointsAsyncTask.AsyncTaskCallback() {
            @Override
            public void callback(String result) {
                mProgressBar.setVisibility(View.GONE);
                Intent sendIntent = new Intent(MainActivity.this, JokeDisplayActivity.class);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result);
                startActivity(sendIntent);
            }
        });
        endPointsAsyncTask.execute(new Pair<Context, String>(MainActivity.this, "name"));

    }


}
