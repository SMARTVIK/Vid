package com.hunter.videostatus.listeners;

import com.hunter.videostatus.gifandvideos.MainPojoGIF;

public interface OnShareItemsListener {
    void onWhatsUpClick(MainPojoGIF.DataBean dataBean);
    void onInstaClick(MainPojoGIF.DataBean dataBean);
    void onMessangerClick(MainPojoGIF.DataBean dataBean);
    void onFacebookClick(MainPojoGIF.DataBean dataBean);
}
