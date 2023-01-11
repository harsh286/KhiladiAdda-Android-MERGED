package com.khiladiadda.utility;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.khiladiadda.interfaces.IOnFileDownloadedListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadApk extends AsyncTask<String, Integer, File> {

    private IOnFileDownloadedListener mListener;
    private int mFileLength;

    public DownloadApk(IOnFileDownloadedListener listener) {
        mListener = listener;
    }

    @SuppressLint("SetWorldReadable") @Override protected File doInBackground(String... urls) {
        String mUrl = urls[0];
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            File dir = new File(AppConstant.APP_DIRECTORY_PATH);
            //            File dir = new File(mContext.getFilesDir(), AppConstants.APP_FOLDER_PATH);
            if (!dir.isDirectory()) dir.mkdir();
            String fileName = mUrl.substring(mUrl.lastIndexOf("/"), mUrl.length()).replace(" ", "_");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + fileName);

            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(90000);
            connection.setReadTimeout(90000);
            connection.setInstanceFollowRedirects(true);
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            mFileLength = connection.getContentLength();

//            Log.e("file length", ": " + mFileLength);

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(file);

            float totalMB = (mFileLength * 1.0f) / 1024 / 1024;

            //            copyStream(input, output);
            byte[] data = new byte[1024 * 1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (mFileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / mFileLength));

                output.write(data, 0, count);
            }
            output.close();
            input.close();
            file.setReadable(true, false);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    @Override protected void onPostExecute(File file) {
        if (file != null && mListener != null) {
            mListener.onFileDownloaded(file.getAbsolutePath());
        }
    }

    @Override protected void onProgressUpdate(Integer... progress) {
        if (mListener != null) {
            mListener.onFileProgressUpdate(progress[0], mFileLength);
        }
    }

    private void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1) break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}