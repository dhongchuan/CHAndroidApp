package com.dhongchuan.chapplication.activitys;

import android.app.Fragment;

import com.dhongchuan.chapplication.base.BaseFragmentActivity;
import com.dhongchuan.chapplication.fragments.CustomLinearLayoutFragment;

/**
 * Created by dhongchuan on 15/2/3.
 */
public class CustomLinearLayoutActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CustomLinearLayoutFragment();
    }
}
