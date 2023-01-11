package com.khiladiadda.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.webkit.MimeTypeMap;

public class ImageUtils {
    /**
     * This method is used to return the bitmap chosen by user
     *
     * @param path
     * @param mat
     * @param size
     * @return Bitmap
     */
    public static Bitmap getProfileImageBitmap(String path, Matrix mat, int size) {
        size = 250;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = Math.min(options.outWidth / size, options.outHeight / size);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        //bitmap = getBitmap(bitmap, size, size, mat);
        return bitmap;
    }

    /**
     * This method is used to get bitmap using new height and width, and the
     * matrix
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @param mat
     * @return Bitmap
     */
    private static Bitmap getBitmap(Bitmap bm, int newWidth, int newHeight, Matrix mat) {
        if (newHeight >= bm.getHeight()) {
            newHeight = bm.getHeight();
        }
        if (newWidth >= bm.getWidth()) {
            newWidth = bm.getWidth();
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        resizedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
        return resizedBitmap;
    }

    /**
     * This method is used to get the rotaion matrix
     *
     * @param orientation
     * @return Matrix
     */
    public static Matrix getRotationMatrix(int orientation) {
        Matrix mat = new Matrix();
        if (orientation == 1) {
            mat.postRotate(0);
        } else if (orientation == 6) {
            mat.postRotate(90);
        } else if (orientation == 8) {
            mat.postRotate(270);
        } else if (orientation == 3) {
            mat.postRotate(180);
        }
        return mat;
    }

    /**
     * This method is used to get the orientation of captured image
     *
     * @param path
     * @return int
     */
    public static int getOrientation(String path) {
        int orientation = 0;
        try {
            ExifInterface exif = new ExifInterface(path);
            String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            orientation = Integer.parseInt(exifOrientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orientation;
    }

    /**
     * This method is used to save image to file
     *
     * @param bitmap
     * @param path
     * @return void
     */
    public static void saveImageToFile(Bitmap bitmap, String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (path.contains(".png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        try {
            String directoryPath = AppConstant.APP_DIRECTORY_PATH;
            File file = new File(directoryPath);
            if (!file.isDirectory()){
                file.mkdir();
            }
            File f = new File(path);
            if (f.isFile()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static void deleteImage(String path) {
        File f = new File(path);
        if (f.isFile()) {
            f.delete();
        }
    }

    public static String getExtension(String url) {
        return url.substring(url.lastIndexOf("."));
    }

    /**
     * This method is used to save image to file
     *
     * @param bitmap
     * @param path
     * @return void
     */
    public static void saveLudoImageToFile(Bitmap bitmap, String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (path.contains(".png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        try {
            String directoryPath = AppConstant.APP_DIRECTORY_PATH;
            File file = new File(directoryPath);
            if (!file.isDirectory()){
                file.mkdir();
            }
            File f = new File(path);
            if (f.isFile()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to save image to file
     *
     * @param bitmap
     * @param path
     * @return void
     */
    public static void saveAadharImageToFile(Bitmap bitmap, String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (path.contains(".png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        try {
            String directoryPath = AppConstant.APP_DIRECTORY_PATH;
            File file = new File(directoryPath);
            if (!file.isDirectory()){
                file.mkdir();
            }
            File f = new File(path);
            if (f.isFile()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}