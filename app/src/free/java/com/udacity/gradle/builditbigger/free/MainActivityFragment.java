package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.Observables;
import com.udacity.gradle.builditbigger.R;

import app.minimize.com.androidjokesviewer.JokeViewerActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    InterstitialAd mInterstitialAd;
    ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        final Observables observables = new Observables();
        final AdView mAdView = (AdView) root.findViewById(R.id.adView);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                loadJoke(observables);
            }
        });

        requestNewInterstitial();
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        root.findViewById(R.id.buttonTellJoke)
                .setOnClickListener(v -> {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        loadJoke(observables);
                    }
                });

        return root;
    }

    private void loadJoke(final Observables observables) {
        mProgressBar.setVisibility(View.VISIBLE);
        observables.getJokeObservable(observables.getMyApi())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jokeString -> {
                    mProgressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(), JokeViewerActivity.class);
                    intent.putExtra(JokeViewerActivity.JOKE, jokeString);
                    startActivity(intent);
                }, throwable -> {
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    mProgressBar.setVisibility(View.GONE);
                });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
