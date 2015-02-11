package com.dhongchuan.chapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dhongchuan.chapplication.R;

/**
 * Created by dhongchuan on 15/2/7.
 */
public class SplashScreenFargment extends Fragment {
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.load_progressbar);
        return view;
    }

    public void setProgress(int progress){
        progressBar.setProgress(progress);
    }
}
