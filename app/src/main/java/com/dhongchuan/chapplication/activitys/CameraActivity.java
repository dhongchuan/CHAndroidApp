package com.dhongchuan.chapplication.activitys;

import android.app.Fragment;
import android.os.Bundle;

import com.dhongchuan.chapplication.base.BaseFragmentActivity;
import com.dhongchuan.chapplication.fragments.CameraFragment;

/**
 * Created by dhongchuan on 15/1/29.
 */
public class CameraActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new CameraFragment();
    }
}
