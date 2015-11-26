package com.udacity.gradle.builditbigger;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

import rx.schedulers.Schedulers;

/**
 * Created by ahmedrizwan on 16/11/2015.
 */
public class AsyncTests extends ActivityInstrumentationTestCase2<MainActivity> {
    Observables mObservables;

    public AsyncTests() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() {
        mObservables = new Observables();
    }

    public AsyncTests(final Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @MediumTest
    public void testAsyncReturnsJoke() {
        String jokeString = "";
        try {
            jokeString = mObservables.getJokeObservable(mObservables.getMyApi())
                    .subscribeOn(Schedulers.newThread())
                    .toBlocking()
                    .first();
        } catch (Exception e) {
            Log.e("Exception",e.getMessage());
        }
        //Assert that a string is returned
        assertTrue(jokeString.length() > 0);
    }
}
