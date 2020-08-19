package com.jeff.funapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import com.github.chrisbanes.photoview.PhotoView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageFragment extends Fragment {

    PhotoView imageView;
    File[] myFilePath;
    int imgPointer = 0;
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

        imageView = (PhotoView) view.findViewById(R.id.photo_view);

        Log.d("BufferInput", "file len = " + myFilePath[imgPointer].length());

        //read input image from file
        byte[] bytes = new byte[(int) myFilePath[imgPointer].length()];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(myFilePath[imgPointer]));
            int sucs = bufferedInputStream.read(bytes, 0, bytes.length);
            bufferedInputStream.close();
            Log.d("bufferInput", "==" + sucs);
            Log.d("bufferInput", "bytes = " + bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        imageView.setImageBitmap(bitmap);

        //debug
        assert myFilePath != null;
        for (File file : myFilePath) {
            Log.d("Image Fragment openFile", "" + file);
        }

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPointer -1 <= 0) imgPointer = myFilePath.length;
                imageView.setImageURI(Uri.fromFile(myFilePath[--imgPointer]));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPointer >= myFilePath.length - 1) imgPointer = -1;
                imageView.setImageURI(Uri.fromFile(myFilePath[++imgPointer]));
            }
        });

        return view;
    }

    static public ImageFragment getInstance(File[] files){
        return new ImageFragment(files);
    }
}
