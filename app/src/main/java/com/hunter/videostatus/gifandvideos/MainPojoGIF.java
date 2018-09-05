package com.hunter.videostatus.gifandvideos;

import com.google.gson.Gson;

import java.util.List;

public class MainPojoGIF {

    /**
     * status : 1
     * pages : 1
     * cur_page : 1
     * data : [{"id":"2708","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (1).gif"},{"id":"2707","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (23).gif"},{"id":"2706","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (22).gif"},{"id":"2705","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (21).gif"},{"id":"2704","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (20).gif"},{"id":"2703","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (19).gif"},{"id":"2702","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (18).gif"},{"id":"2701","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (17).gif"},{"id":"2700","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (16).gif"},{"id":"2699","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (15).gif"},{"id":"2698","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (14).gif"},{"id":"2697","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (13).gif"},{"id":"2696","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (12).gif"},{"id":"2695","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (11).gif"},{"id":"2694","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (10).gif"},{"id":"2693","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (9).gif"},{"id":"2692","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (8).gif"},{"id":"2691","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (7).gif"},{"id":"2690","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (6).gif"},{"id":"2689","cat_id":"48","url":"gif_for_whatsapp_app/upload/utrayan (5).gif"}]
     */

    private int status;
    private int pages;
    private int cur_page;
    private List<DataBean> data;

    public static MainPojoGIF objectFromData(String str) {

        return new Gson().fromJson(str, MainPojoGIF.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCur_page() {
        return cur_page;
    }

    public void setCur_page(int cur_page) {
        this.cur_page = cur_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2708
         * cat_id : 48
         * url : gif_for_whatsapp_app/upload/utrayan (1).gif
         */

        private String id;
        private String cat_id;
        private String url;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
