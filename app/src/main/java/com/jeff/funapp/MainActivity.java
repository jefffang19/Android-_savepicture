package com.jeff.funapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final int imageRequestCode = 1;
    SaveFiles saveFiles = new SaveFiles();

    File[] myFilePath;

    Button savePic;
    Button viewPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savePic = findViewById(R.id.savePic);
        viewPic = findViewById(R.id.viewPics);

        //load files
        myFilePath = getBaseContext().getFilesDir().listFiles();
        assert myFilePath != null;
        for (File file : myFilePath) {
            Log.d("openFile", "" + file);
        }


        savePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update file list before every action
                myFilePath = getBaseContext().getFilesDir().listFiles();
                Intent intent =  new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, imageRequestCode);
            }
        });

        viewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update file list before every action
                myFilePath = getBaseContext().getFilesDir().listFiles();
                if(myFilePath.length == 0) return;
                findViewById(R.id.savedImg).setVisibility(View.GONE);
                findViewById(R.id.savePic).setVisibility(View.GONE);
                findViewById(R.id.viewPics).setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.FragmentContainer, ImageFragment.getInstance(myFilePath)).commit();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == imageRequestCode){
            if(resultCode == RESULT_OK){
                assert data != null;
                Uri uri = data.getData();

                Log.d("MainActivity", "uri = " + uri);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageView imageView = findViewById(R.id.savedImg);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Log.d("beforeSaveImg", "done");
                Log.d("beforeSaveImg", "bitmap size = "+ bitmap.toString());

                //save file
                try {
                    assert bitmap != null;
                    FileOutputStream outputStream = openFileOutput(("" + (myFilePath.length + 1) + ".jpg") , Context.MODE_PRIVATE);
                    saveFiles.saveFile(outputStream, bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}