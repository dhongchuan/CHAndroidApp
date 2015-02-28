package com.dhongchuan.chapplication.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dhongchuan.chapplication.R;
import com.dhongchuan.chapplication.services.ImageLoaderService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomLinearLayoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomLinearLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomLinearLayoutFragment extends Fragment {
    String url = "http://c2.hoopchina.com.cn/uploads/star/event/images/150212/bmiddle-4c6852698d93bb8cce7199a2a8cefff01aec988b.jpg";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_linear_layout, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        ImageLoaderService.with(getActivity().getApplicationContext()).load(imageView, url);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button", "onClick");

            }
        });
        return view;
    }

}
