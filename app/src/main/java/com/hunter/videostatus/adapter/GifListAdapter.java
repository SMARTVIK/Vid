package com.hunter.videostatus.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hunter.videostatus.R;
import com.hunter.videostatus.gifandvideos.ApiClient;
import com.hunter.videostatus.gifandvideos.MainPojoGIF;
import com.hunter.videostatus.listeners.OnItemClickListener;
import com.hunter.videostatus.listeners.OnShareItemsListener;
import com.hunter.videostatus.ui.activity.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GifListAdapter extends RecyclerView.Adapter<GifListAdapter.ViewHolder> {

    private final Context context;
    List<MainPojoGIF.DataBean> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnShareItemsListener onShareItemsListener;

    public GifListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GifListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GifListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_item_share, parent, false));
    }

    @Override
    public void onBindViewHolder(GifListAdapter.ViewHolder holder, int position) {
        MainPojoGIF.DataBean dataBean = data.get(position);
        Glide.with(context).load(ApiClient.BASE_URL_GIF + dataBean.getUrl()).into(holder.gifImage);
        holder.gifText.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<MainPojoGIF.DataBean> dataBeans) {
        this.data = dataBeans;
        notifyDataSetChanged();
    }

    public void setOnShareItemsListener(OnShareItemsListener onShareItemsListener) {
        this.onShareItemsListener = onShareItemsListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gifImage;
        TextView gifText;
        private ImageView shareButton;
        private ImageView messangerButton;
        private ImageView instagramButton;
        private ImageView whatsAppButton;


        public ViewHolder(final View itemView) {
            super(itemView);
            gifImage = itemView.findViewById(R.id.gif_image);
            gifText = itemView.findViewById(R.id.gif_text);
            shareButton = itemView.findViewById(R.id.share);
            messangerButton = itemView.findViewById(R.id.send_to_facebook);
            instagramButton = itemView.findViewById(R.id.send_to_instagram);
            whatsAppButton = itemView.findViewById(R.id.send_to_whatsapp);

            messangerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShareItemsListener.onMessangerClick(data.get(getLayoutPosition()));
                }
            });

            instagramButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onShareItemsListener.onInstaClick(data.get(getLayoutPosition()));
                }
            });

            whatsAppButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShareItemsListener.onWhatsUpClick(data.get(getLayoutPosition()));
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShareItemsListener.onFacebookClick(data.get(getLayoutPosition()));
                }
            });
        }
    }

}
