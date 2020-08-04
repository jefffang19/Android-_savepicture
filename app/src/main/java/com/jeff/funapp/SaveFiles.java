package com.jeff.funapp;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFiles {

    public void saveFile(final FileOutputStream fileOutputStream, final Bitmap bitmap) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fileOutputStream.write(bitmapToByte(bitmap));
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
