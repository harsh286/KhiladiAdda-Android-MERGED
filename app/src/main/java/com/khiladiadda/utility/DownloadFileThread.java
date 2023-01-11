package com.khiladiadda.utility;

import android.content.Context;
import android.util.Log;

import com.khiladiadda.interfaces.IOnFileDownloadedListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileThread extends Thread {
    private IOnFileDownloadedListener mListener;
    private String mUrl;

    public DownloadFileThread(Context context, String url, IOnFileDownloadedListener listener) {
        mListener = listener;
        mUrl = url;
    }

    @Override
    public void run() {
        try {
            File dir = new File(AppConstant.APP_DIRECTORY_PATH);
//            File dir = new File(mContext.getFilesDir(), AppConstants.APP_FOLDER_PATH);
            if (!dir.isDirectory())
                dir.mkdir();
            String fileName = mUrl.substring(mUrl.lastIndexOf("/"), mUrl.length()).replace(" ", "_");
//            Log.e("url", "-->" + mUrl);
//            Log.e("name", "-->" + fileName);
            File file = new File(AppConstant.APP_DIRECTORY_PATH + File.separator + fileName);
//            File file = new File(mContext.getFilesDir() +File.separator + AppConstants.APP_FOLDER_PATH + File.separator + fileName);
//            Log.e("Path is", "" + file);
            URL imageUrl = new URL(mUrl.replace(" ", "%20"));
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(90000);
            conn.setReadTimeout(90000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(file);
            copyStream(is, os);
            os.close();
            if (mListener != null) {
                mListener.onFileDownloaded(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to copy input stream into output stream
     *
     * @param is
     * @param os
     * @return void
     */
    private void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
