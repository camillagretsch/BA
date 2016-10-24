package com.example.woko_app.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 27.09.16.
 */
public class PersonalSerializer {

    /**
     * convert from bitmap to byte array
     * @param bitmap
     * @return
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * convert from byte array to bitmap
     * @param image
     * @return
     */
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * convert from boolean to string
     * @param check
     * @return
     */
    public static List<String> convertBoolean(List<Boolean> check) {
        List<String> tmp = new ArrayList<>();
        for (Boolean b : check) {
            tmp.add(String.valueOf(b));
        }
        return tmp;
    }

    /**
     * convert fron integer to string
     * @param countBroken
     * @return
     */
    public static List<String> convertInteger(List<Integer> countBroken) {
        List<String> tmp = new ArrayList<>(countBroken.size());
        for (Integer myInt : countBroken) {
            tmp.add(String.valueOf(myInt));
        }
        return tmp;
    }
}
