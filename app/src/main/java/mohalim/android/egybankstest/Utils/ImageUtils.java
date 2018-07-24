package mohalim.android.egybankstest.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;

public class ImageUtils {


    public static byte[] getByteFromBitmap(Bitmap image, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image = getResizedBitmap(image,250);
        image.compress(Bitmap.CompressFormat.JPEG, maxSize, baos);
        byte[] data = baos.toByteArray();
        return data;
    }


    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }



}
