package com.hunter.videostatus.listeners;

import com.hunter.videostatus.model.Status;

import java.util.ArrayList;

public interface OnPopUpShareListener {
    void onWhatsUpClick(Status.DataBean dataBean);

    void onInstaClick(Status.DataBean dataBean);

    void onMessangerClick(Status.DataBean dataBean);

    void onFacebookClick(Status.DataBean dataBean);

    void onItemClick(Status.DataBean bean, ArrayList<Status.DataBean> results);
}
