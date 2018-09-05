package com.hunter.videostatus.ui.activity;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.videostatus.R;
import com.hunter.videostatus.listeners.OnAppItemClick;

import java.util.ArrayList;
import java.util.List;

class AllAppsAdapter extends RecyclerView.Adapter<AllAppsAdapter.ViewHolder> {

    List<ResolveInfo> pkgAppsList = new ArrayList<>();
    private OnAppItemClick onItemClick;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResolveInfo app = pkgAppsList.get(position);
        holder.appName.setText(getAppName(app,holder.itemView.getContext().getPackageManager()));
        holder.appDescription.setText(app.activityInfo.applicationInfo.packageName);
        holder.appIcon.setImageDrawable(app.loadIcon(holder.itemView.getContext().getPackageManager()));
    }

    @Override
    public int getItemCount() {
        return pkgAppsList.size();
    }

    public void setData(List<ResolveInfo> pkgAppsList) {
        this.pkgAppsList = pkgAppsList;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnAppItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        TextView appDescription;
        ImageView appIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.app_name);
            appDescription = itemView.findViewById(R.id.app_package_name);
            appIcon = itemView.findViewById(R.id.app_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(pkgAppsList.get(getLayoutPosition()));
                }
            });
        }
    }

    private String getAppName(ResolveInfo resolveInfo, PackageManager pm) {
        try {
            String package_name = resolveInfo.activityInfo.packageName;
            String app_name = (String) pm.getApplicationLabel(pm.getApplicationInfo(package_name, PackageManager.GET_META_DATA));
            return app_name;
            //Log.i("Check", "package = <" + package_name + "> name = <" + app_name + ">");
        } catch (Exception e) {
        }
        return "";
    }
}
