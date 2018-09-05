package com.hunter.videostatus.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hunter.videostatus.R;
import com.hunter.videostatus.model.Status;
import com.hunter.videostatus.ui.activity.VideoDetailScreen;
import com.hunter.videostatus.util.ColorGenerator;
import com.hunter.videostatus.util.Utility;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class LatestVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PROGRESS_VIEW = 2;
    private static final int NORMAL_VIEW = 1;
    private final Context context;
    private ArrayList<Status.DataBean> results = new ArrayList<>();
    private String type = "Video";
    private boolean showProgress;

    public LatestVideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (PROGRESS_VIEW == viewType) {
            viewHolder =  new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_view, parent, false));
        } else {
            viewHolder =  new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false));
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (showProgress && position == results.size()) {
            return PROGRESS_VIEW;
        } else {
            return NORMAL_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof MainViewHolder) {
            Status.DataBean dataBean = results.get(position);
            final LatestVideoAdapter.MainViewHolder holder = (LatestVideoAdapter.MainViewHolder) holder1;
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
                Utility.loadImageFromGlide(context,dataBean.getImgurl(),holder.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return showProgress ? results.size() + 1 : results.size();
    }

    public void setType(String catString) {
        this.type = catString;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private View shareLayout;

        private ImageView copyButton;
        private ImageView messangerButton;
        private ImageView instagramButton;
        private ImageView whatsAppButton;

        private ImageView imageView;
        private ImageView playButton;
        private TextView title;

        public MainViewHolder(final View itemView) {
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
                    Toast.makeText(itemView.getContext(), "Copied", Toast.LENGTH_LONG).show();
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
                    if (type.equals("Video")) {
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), VideoDetailScreen.class).putExtra("item", results.get(getLayoutPosition()))
                                .putParcelableArrayListExtra("remaining_list", results));
                    }
                }
            });
        }
    }

    public void setData(ArrayList<Status.DataBean> data) {
        this.results = data;
        notifyDataSetChanged();
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder{

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
