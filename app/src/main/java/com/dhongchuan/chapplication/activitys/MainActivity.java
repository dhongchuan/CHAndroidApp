package com.dhongchuan.chapplication.activitys;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.dhongchuan.chapplication.R;
import com.dhongchuan.chapplication.base.BaseFragmentActivity;
import com.dhongchuan.chapplication.fragments.ComponentListFragment;
import com.dhongchuan.chapplication.fragments.SplashScreenFargment;


public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_template);
        FragmentManager fm = getFragmentManager();
        Fragment mainFragment = fm.findFragmentById(R.id.main_fragment_container);
        if (mainFragment == null) {
            mainFragment = new ComponentListFragment();
            fm.beginTransaction().add(R.id.main_fragment_container, mainFragment).commit();
        }
    }

    @Override
    protected Fragment createFragment() {
        return new ComponentListFragment();
    }
}
