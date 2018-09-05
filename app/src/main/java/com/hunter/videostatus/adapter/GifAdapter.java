package com.hunter.videostatus.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hunter.videostatus.R;
import com.hunter.videostatus.gifandvideos.ApiClient;
import com.hunter.videostatus.gifandvideos.GifCategories;
import com.hunter.videostatus.listeners.OnItemClickListener;
import com.hunter.videostatus.ui.fragments.GifFragment;
import com.hunter.videostatus.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {

    private final Context context;
    List<GifCategories.DataBean> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public GifAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GifCategories.DataBean dataBean = data.get(position);
        Glide.with(context).load(ApiClient.BASE_URL_GIF + dataBean.getCat_images()).into(holder.gifImage);
        holder.gifText.setText(dataBean.getCat_name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<GifCategories.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView gifImage;
        TextView gifText;

        public ViewHolder(View itemView) {
            super(itemView);

            gifImage = itemView.findViewById(R.id.gif_image);
            gifText = itemView.findViewById(R.id.gif_text);

            ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
                @Override
                public Shader resize(int width, int height) {
                    LinearGradient lg = new LinearGradient(0, 0, 0, gifText.getHeight(),
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
                gifText.setBackground(p);
            }
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(data.get(getLayoutPosition()));
        }
    }
}
