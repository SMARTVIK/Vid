package com.hunter.videostatus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.videostatus.R;
import com.hunter.videostatus.listeners.OnItemClick;
import com.hunter.videostatus.model.CategoryModel;
import com.hunter.videostatus.ui.fragments.CategoryFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel.DataBean> results = new ArrayList<>();
    private OnItemClick onItemClickListerner;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryModel.DataBean dataBean = results.get(position);
        Picasso.get()
                .load(dataBean.getCategory_image())
                .placeholder(R.color.white)
                .into(holder.image);
        holder.text.setText(dataBean.getCategory_name());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setData(List<CategoryModel.DataBean> dataBeans) {
        this.results = dataBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListerner(OnItemClick onItemClickListerner) {
        this.onItemClickListerner = onItemClickListerner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public ImageView image;



        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListerner.onItemClick(results.get(getLayoutPosition()));
                }
            });
        }
    }
}
