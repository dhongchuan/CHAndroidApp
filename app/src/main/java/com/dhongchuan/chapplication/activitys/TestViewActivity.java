package com.dhongchuan.chapplication.activitys;

import android.app.Fragment;

import com.dhongchuan.chapplication.base.BaseFragmentActivity;
import com.dhongchuan.chapplication.fragments.SwitcherViewFragment;

/**
 * Created by dhongchuan on 15/2/7.
 */
public class TestViewActivity extends BaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SwitcherViewFragment();
    }
}
