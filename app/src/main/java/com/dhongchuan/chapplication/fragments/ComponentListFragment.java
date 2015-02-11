package com.dhongchuan.chapplication.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dhongchuan.chapplication.activitys.CameraActivity;
import com.dhongchuan.chapplication.activitys.CustomLinearLayoutActivity;
import com.dhongchuan.chapplication.activitys.TestViewActivity;

import java.util.ArrayList;

/**
 * Created by dhongchuan on 15/1/29.
 */
public class ComponentListFragment extends ListFragment {
    private ArrayList<String> mArrComponent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("功能列表");
        init();
    }

    private void init(){
        mArrComponent = new ArrayList<String>();
        mArrComponent.add("相机");
        mArrComponent.add("自定义滑动LinearLayout");
        mArrComponent.add("练习控件专用页面");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mArrComponent);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = null;
        switch (position){
            case 0:
                i = new Intent(getActivity(), CameraActivity.class);
                break;
            case 1:
                i = new Intent(getActivity(), CustomLinearLayoutActivity.class);
                break;
            case 2:
                i = new Intent(getActivity(), TestViewActivity.class);
                break;
        }
        startActivity(i);
    }
}
