package com.dhongchuan.chapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.dhongchuan.chapplication.R;

/**
 * Created by dhongchuan on 15/2/7.
 */
public class SwitcherViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_switcher, container, false);
        final  ViewSwitcher vsSwitcher = (ViewSwitcher) view.findViewById(R.id.view_switcher);
        vsSwitcher.setInAnimation(getActivity(), R.anim.fade_in);
        vsSwitcher.setOutAnimation(getActivity(), R.anim.fade_out);
        Button btnSwitch = (Button) view.findViewById(R.id.switch_button);
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vsSwitcher.showNext();
            }
        });

        return view;
    }
}
