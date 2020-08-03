package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

public class ImageFragment extends Fragment {

    ImageView imageView;
    File[] myFilePath;
    private ImageFragment(File[] files){
        myFilePath = files;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.show_pic, container, false);
        Button prevBtn = view.findViewById(R.id.PreBtn);
        Button nextBtn = view.findViewById(R.id.NextBtn);

        //debug
        assert myFilePath != null;
        for (File file : myFilePath) {
            Log.d("Image Fragment openFile", "" + file);
        }

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    static public ImageFragment getInstance(File[] files){
        return new ImageFragment(files);
    }
}
