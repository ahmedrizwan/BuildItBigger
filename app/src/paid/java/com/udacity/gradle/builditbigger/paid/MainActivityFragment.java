package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.Observables;
import com.udacity.gradle.builditbigger.R;

import app.minimize.com.androidjokesviewer.JokeViewerActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        final Observables observables = new Observables();
        root.findViewById(R.id.buttonTellJoke)
                .setOnClickListener(v -> {
                    mProgressBar.setVisibility(View.VISIBLE);
                    observables.getJokeObservable(observables.getMyApi()).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(jokeString -> {
                                mProgressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getActivity(), JokeViewerActivity.class);
                                intent.putExtra(JokeViewerActivity.JOKE, jokeString);
                                startActivity(intent);
                            }, throwable -> {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                            });
                });
        return root;
    }


}
