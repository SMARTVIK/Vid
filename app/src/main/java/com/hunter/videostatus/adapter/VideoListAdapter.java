package com.hunter.videostatus.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hunter.videostatus.R;
import com.hunter.videostatus.listeners.OnVideoClickListener;
import com.hunter.videostatus.model.Status;
import com.hunter.videostatus.ui.activity.VideoDetailScreen;
import com.hunter.videostatus.util.ColorGenerator;
import com.hunter.videostatus.util.Utility;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Status.DataBean> results = new ArrayList<>();
    private String type = "Video";
    private OnVideoClickListener onItemClickListener;

    public VideoListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Status.DataBean dataBean = results.get(position);
        if (type.equals("Video")) {
            holder.shareLayout.setVisibility(View.GONE);
            holder.title.setVisibility(View.GONE);
            Utility.loadImageFromGlide(context,dataBean.getImgurl(),holder.imageView);
            holder.playButton.setVisibility(View.VISIBLE);
        } else if (type.equals("Text")) {
            holder.shareLayout.setVisibility(View.VISIBLE);
            holder.playButton.setVisibility(View.GONE);
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(dataBean.getTextstatus());
            holder.itemView.setBackgroundColor(ColorGenerator.MATERIAL.getRandomColor());
        } else {
            holder.shareLayout.setVisibility(View.GONE);
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(dataBean.getTextstatus());
            holder.playButton.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(ColorGenerator.MATERIAL.getRandomColor());
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setType(String catString) {
        this.type = catString;
    }

    public void setOnItemClickListener(OnVideoClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View shareLayout;

        private ImageView copyButton;
        private ImageView messangerButton;
        private ImageView instagramButton;
        private ImageView whatsAppButton;

        private ImageView imageView;
        private ImageView playButton;
        private TextView title;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.video_image);
            playButton = itemView.findViewById(R.id.play_button);
            title = itemView.findViewById(R.id.title_text);
            shareLayout = itemView.findViewById(R.id.sharing_layout);
            copyButton = itemView.findViewById(R.id.copy_button);
            messangerButton = itemView.findViewById(R.id.send_to_facebook);
            instagramButton = itemView.findViewById(R.id.send_to_instagram);
            whatsAppButton = itemView.findViewById(R.id.send_to_whatsapp);
            copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", results.get(getLayoutPosition()).getTextstatus());
                    clipboard.setPrimaryClip(clip);
                }
            });

            messangerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            results.get(getLayoutPosition()).getTextstatus());
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.facebook.orca");
                    try {
                        itemView.getContext().startActivity(sendIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(itemView.getContext(), "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
                    }
                }
            });

            instagramButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, results.get(getLayoutPosition()).getTextstatus());
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.instagram.android");
                    try {
                        itemView.getContext().startActivity(sendIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(itemView.getContext(), "Please Install instagram", Toast.LENGTH_LONG).show();
                    }
                }
            });

            whatsAppButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, results.get(getLayoutPosition()).getTextstatus());
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    try {
                        itemView.getContext().startActivity(sendIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(itemView.getContext(), "Please Install whatsapp", Toast.LENGTH_LONG).show();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onVideoClick(results.get(getLayoutPosition()));
                }
            });
        }
    }

    public void setData(ArrayList<Status.DataBean> data) {
        this.results = data;
        notifyDataSetChanged();
    }
}
