package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.minimize.ahmedrizwan.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import app.minimize.com.androidjokesviewer.JokeViewerActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(abstractGoogleClientRequest ->
                        abstractGoogleClientRequest.setDisableGZipContent(true));
        final MyApi myApiService = builder.build();
        root.findViewById(R.id.buttonTellJoke)
                .setOnClickListener(v -> {
                    getJokeObservable(myApiService).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(jokeString -> {
                                Intent intent = new Intent(getActivity(), JokeViewerActivity.class);
                                intent.putExtra(JokeViewerActivity.JOKE, jokeString);
                                startActivity(intent);
                            }, throwable -> {
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                            });
                });

        return root;
    }

    public static Observable<String> getJokeObservable(final MyApi myApiService) {
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
}
