package com.dhongchuan.chapplication.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dhongchuan.chapplication.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by dhongchuan on 15/1/29.
 */
public class CameraFragment extends Fragment {
    private Camera mCamera;
    private SurfaceView mSurfeceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);

        Button btnTakePicture = (Button) v.findViewById(R.id.confirm);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mSurfeceView = (SurfaceView) v.findViewById(R.id.camera_surface_view);
        SurfaceHolder holder = mSurfeceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback(){

            /**
             * 包含SurfaceVeiw的视图层级被放到屏幕上时调用该方法
             * Surface与客户端进行关联
             * @param surfaceHolder
             */
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (mCamera != null){
                    try {
                        mCamera.setPreviewDisplay(surfaceHolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            /**
             * Surface首次显示时调用
             * @param surfaceHolder
             * @param format
             * @param w
             * @param h
             */
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
                if (mCamera == null)
                    return;
                Camera.Parameters parameters = mCamera.getParameters();
                Camera.Size size = getBestSize(parameters.getSupportedPreviewSizes(), w, h);
                parameters.setPreviewSize(size.width, size.height);

                try{
                    mCamera.startPreview();

                }catch (Exception e){
                    mCamera.release();
                    mCamera = null;
                }


            }

            /**
             * SurfaceView从屏幕上移除时调用
             * Surface的客户端停止使用Surface
             * @param surfaceHolder
             */
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if(mCamera != null){
                    mCamera.stopPreview();
                }
            }
        });
        return v;
    }

    @TargetApi(9)
    @Override
    public void onResume() {
        super.onResume();
        mCamera = Camera.open(0);

    }

    @Override
    public void onPause() {
        super.onPause();
        if(mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.Size getBestSize(List<Camera.Size> sizeList, int w, int h){
        Camera.Size bestSize = sizeList.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for(Camera.Size size:sizeList){
            int area = size.width * size.height;
            if(area > largestArea){
                bestSize = size;
                largestArea = area;
            }
        }
        return bestSize;
    }
}
