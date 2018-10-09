package com.hunter.videostatus.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hunter.videostatus.R;

public class Utility {

    public static void showLoader(View mRootView) {
        mRootView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.rv_latest_video_status).setVisibility(View.GONE);
    }

    public static void hideLoader(View mRootView) {
        mRootView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        mRootView.findViewById(R.id.rv_latest_video_status).setVisibility(View.VISIBLE);
    }

    public static String getPackageName(int current) {
        String packageName = null;
        switch (current){
            case 1:
                packageName = "com.whatsapp";
                break;
            case 2:
                packageName = "com.instagram.android";
                break;
            case 3:
                packageName = "com.facebook.orca";
                break;
            case 4:
                packageName = "com.facebook.katana";
                break;
        }
        return packageName;
    }

    public static void loadImageFromGlide(Context context, String imageUrl, final ImageView imageView) {
        imageView.setImageDrawable(null);
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .thumbnail(0.5f)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public static boolean checkPermissionForStorage(Activity context) {
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
}
