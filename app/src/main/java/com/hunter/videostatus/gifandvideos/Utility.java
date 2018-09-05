package com.hunter.videostatus.gifandvideos;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hunter.videostatus.R;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.Request;

public class Utility {
    private static Utility mSelf;
    private Context context = null;
    private int deviceHeight;
    private int deviceWidth;
    private ProgressDialog mProgressDialog;
    public Typeface typeFaceBold = null;
    public Typeface typeFaceNormal = null;

    private Utility() {
    }

    public static Utility getInstance() {
        if (mSelf == null) {
            mSelf = new Utility();
        }
        return mSelf;
    }

    public void init(Context context) {
        this.context = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        this.deviceWidth = displayMetrics.widthPixels;
        this.deviceHeight = displayMetrics.heightPixels;
        this.typeFaceBold = Typeface.createFromAsset(context.getAssets(), "fonts/hindi.ttf");
        this.typeFaceNormal = Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf");
    }

    public boolean isInternetAvailable(Context context, boolean isShow) {
        boolean isConnected;
        NetworkInfo networkinfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected() && networkinfo.isAvailable()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
        if (isConnected) {
            return true;
        }
        if (isShow) {
            showToast(context, context.getString(R.string.internet_error));
        }
        return false;
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void setFontNormal(TextView view) {
        view.setTypeface(this.typeFaceNormal);
    }

    public void setFontBold(TextView view) {
        view.setTypeface(this.typeFaceBold);
    }

    public int getDeviceHeight() {
        return this.deviceHeight;
    }

    public int getDeviceWidth() {
        return this.deviceWidth;
    }
/*
    public final void setVolleyConnectionTimeout(Request request) {
        request.setRetryPolicy(new DefaultRetryPolicy(100000, 1, 1.0f));
    }*/

    public void logI(String TAG, String MESSAGE) {
    }

    public void logI(String message) {
    }

    public final void progressDialogShow(Context context, String message, boolean isCancel) {
        this.mProgressDialog = new ProgressDialog(context);
        this.mProgressDialog.requestWindowFeature(1);
        this.mProgressDialog.setMessage(message);
        this.mProgressDialog.setCancelable(isCancel);
        this.mProgressDialog.setIndeterminate(true);
        this.mProgressDialog.show();
    }

    public boolean isProgressDialogShowing() {
        return this.mProgressDialog != null && this.mProgressDialog.isShowing();
    }

    public void progressDialogDismiss() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
        }
    }

    public void saveImage(byte[] response, String fileName) {
        File myDir = new File(Environment.getExternalStorageDirectory().toString() + File.separator + this.context.getResources().getString(R.string.app_folder_name));
        myDir.mkdirs();
        try {
            FileOutputStream out = new FileOutputStream(new File(myDir, fileName));
            out.write(response);
            out.flush();
            out.close();
        } catch (Exception e) {
            logErrror(e);
        }
    }

    public boolean isFileExists(String fileName) {
        if (!isExternalStorageAvailable() && isExternalStorageReadOnly()) {
            return false;
        }
        File myDir = new File(Environment.getExternalStorageDirectory().toString() + File.separator + this.context.getResources().getString(R.string.app_folder_name));
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        return new File(myDir, fileName).exists();
    }

    public File getFileDir(String fileName) {
        File myDir = new File(Environment.getExternalStorageDirectory().toString() + File.separator + this.context.getResources().getString(R.string.app_folder_name));
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        return new File(myDir, fileName);
    }

    public File getAppDir() {
        File myDir = new File(Environment.getExternalStorageDirectory().toString() + File.separator + this.context.getResources().getString(R.string.app_folder_name));
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        return myDir;
    }

    private static boolean isExternalStorageReadOnly() {
        if ("mounted_ro".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public void logErrror(Exception e) {
    }

    public Typeface getTypeFaceBold() {
        return this.typeFaceBold;
    }

    public Typeface getTypeFaceNormal() {
        return this.typeFaceNormal;
    }
}