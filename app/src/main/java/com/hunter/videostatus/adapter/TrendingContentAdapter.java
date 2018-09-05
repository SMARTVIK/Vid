package com.hunter.videostatus.adapter;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.hunter.videostatus.R;
import com.hunter.videostatus.model.Status;
import com.hunter.videostatus.ui.activity.VideoDetailScreen;
import com.hunter.videostatus.util.ColorGenerator;
import com.hunter.videostatus.util.Utility;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrendingContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PROGRESS_VIEW = 2;
    private static final int NORMAL_VIEW = 1;
    private final Context context;
    private ArrayList<Status.DataBean> results = new ArrayList<>();
    private String type = "Video";

    public TrendingContentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (PROGRESS_VIEW == viewType) {
            viewHolder = new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_view, parent, false));
        } else {
            viewHolder = new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof MainViewHolder && position < results.size()) {
            final MainViewHolder holder = (MainViewHolder) holder1;
            Status.DataBean dataBean = results.get(position);
            if (type.equals("Video")) {
                holder.moreAction.setVisibility(View.VISIBLE);
                holder.shareLayout.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
                Utility.loadImageFromGlide(context, dataBean.getImgurl(), holder.imageView);
                holder.playButton.setVisibility(View.VISIBLE);
            } else if (type.equals("Text")) {
                holder.moreAction.setVisibility(View.GONE);
                holder.shareLayout.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(dataBean.getTextstatus());
                holder.playButton.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(ColorGenerator.MATERIAL.getRandomColor());
            } else {
                holder.moreAction.setVisibility(View.GONE);
                holder.shareLayout.setVisibility(View.GONE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(dataBean.getTextstatus());
                holder.playButton.setVisibility(View.GONE);
                Utility.loadImageFromGlide(context, dataBean.getImgurl(), holder.imageView);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == results.size()) ? PROGRESS_VIEW : NORMAL_VIEW;
    }

    @Override
    public int getItemCount() {
        return results.size() == 0 ? 0 : results.size() + 1;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView playButton;
        private TextView title;
        private View shareLayout;

        private ImageView copyButton;
        private ImageView messangerButton;
        private ImageView instagramButton;
        private ImageView whatsAppButton;

        private ImageView moreAction;

        public MainViewHolder(final View itemView) {
            super(itemView);
            moreAction = itemView.findViewById(R.id.more_action);
            shareLayout = itemView.findViewById(R.id.sharing_layout);
            imageView = itemView.findViewById(R.id.video_image);
            playButton = itemView.findViewById(R.id.play_button);
            title = itemView.findViewById(R.id.title_text);

            copyButton = itemView.findViewById(R.id.copy_button);
            messangerButton = itemView.findViewById(R.id.send_to_facebook);
            instagramButton = itemView.findViewById(R.id.send_to_instagram);
            whatsAppButton = itemView.findViewById(R.id.send_to_whatsapp);

            ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
                @Override
                public Shader resize(int width, int height) {
                    LinearGradient lg = new LinearGradient(0, 0, 0, moreAction.getHeight(),
                            new int[] {
                                    Color.parseColor("#00000000"),Color.parseColor("#60000000"),Color.parseColor("#70000000"),Color.parseColor("#80000000") }, //substitute the correct colors for these
                            new float[] {
                                    0, 0.45f, 0.55f, 1 },
                            Shader.TileMode.REPEAT);
                    return lg;
                }
            };
            PaintDrawable p = new PaintDrawable();
            p.setShape(new RectShape());
            p.setShaderFactory(sf);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                moreAction.setBackground(p);
            }
            moreAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (type.equals("Video")) {
                        showPopUpMenu(results.get(getLayoutPosition()),v);
                    }
                }
            });

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
                    } catch (ActivityNotFoundException ex) {
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
                    } catch (ActivityNotFoundException ex) {
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
                    } catch (ActivityNotFoundException ex) {
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

    private void showPopUpMenu(Status.DataBean bean, View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popupMenu.setGravity(Gravity.TOP);
        }
        popupMenu.inflate(R.menu.menu_drawer);
        popupMenu.show();
    }

    public void setData(ArrayList<Status.DataBean> data) {
        this.results = data;
        notifyDataSetChanged();
    }

    public void setType(String catString) {
        this.type = catString;
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

}
