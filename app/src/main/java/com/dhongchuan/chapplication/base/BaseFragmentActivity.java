package com.dhongchuan.chapplication.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.dhongchuan.chapplication.R;

/**
 * Created by dhongchuan on 15/1/29.
 */
public abstract class BaseFragmentActivity extends Activity {
    FragmentManager fm;
    Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_template);
        fm = getFragmentManager();
        mFragment = fm.findFragmentById(R.id.main_fragment_container);
        if(mFragment == null){
            mFragment = createFragment();
            fm.beginTransaction().add(R.id.main_fragment_container, mFragment).commit();
        }
    }

    protected abstract Fragment createFragment();
}
