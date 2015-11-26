package com.udacity.gradle.builditbigger;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.minimize.ahmedrizwan.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ahmedrizwan on 15/11/2015.
 *
 */
public class Observables {

    public Observable<String> getJokeObservable(final MyApi myApiService) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(
                            myApiService.getJoke()
                                    .execute()
                                    .getJoke());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public MyApi getMyApi() {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(abstractGoogleClientRequest ->
                        abstractGoogleClientRequest.setDisableGZipContent(true));
        return builder.build();
    }
}
