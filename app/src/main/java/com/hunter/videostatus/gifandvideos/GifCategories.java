package com.hunter.videostatus.gifandvideos;

import com.google.gson.Gson;

import java.util.List;

public class GifCategories {

    /**
     * status : 1
     * data : [{"order_asce":"0","cat_id":"51","cat_name":"Get Well Soon","cat_images":"gif_for_whatsapp_app/upload/category/2857-2018-08-16.gif"},{"order_asce":"1","cat_id":"7","cat_name":"Good Morning","cat_images":"gif_for_whatsapp_app/upload/category/gm7.gif"},{"order_asce":"2","cat_id":"6","cat_name":"Good Night","cat_images":"gif_for_whatsapp_app/upload/category/7178-2018-07-03.gif"},{"order_asce":"5","cat_id":"15","cat_name":"Funny GIF","cat_images":"gif_for_whatsapp_app/upload/category/funny1.gif"},{"order_asce":"10","cat_id":"3","cat_name":"Birth Day","cat_images":"gif_for_whatsapp_app/upload/category/bday1.gif"},{"order_asce":"12","cat_id":"9","cat_name":"Kiss","cat_images":"gif_for_whatsapp_app/upload/category/kiss6.gif"},{"order_asce":"13","cat_id":"2","cat_name":"Love","cat_images":"gif_for_whatsapp_app/upload/category/love7.gif"},{"order_asce":"14","cat_id":"11","cat_name":"Miss you","cat_images":"gif_for_whatsapp_app/upload/category/missyou7.gif"},{"order_asce":"15","cat_id":"5","cat_name":"Sorry","cat_images":"gif_for_whatsapp_app/upload/category/sorry3.gif"},{"order_asce":"16","cat_id":"8","cat_name":"Hi Hello","cat_images":"gif_for_whatsapp_app/upload/category/hi3.gif"},{"order_asce":"20","cat_id":"19","cat_name":"Mahadev","cat_images":"gif_for_whatsapp_app/upload/category/img3.gif"},{"order_asce":"22","cat_id":"20","cat_name":"Ganesh","cat_images":"gif_for_whatsapp_app/upload/category/gif35.gif"},{"order_asce":"24","cat_id":"24","cat_name":"Janmashtami Gif","cat_images":"gif_for_whatsapp_app/upload/category/janamashtmi (25).gif"},{"order_asce":"25","cat_id":"25","cat_name":"Rakhi Gif","cat_images":"gif_for_whatsapp_app/upload/category/rakhi (3).gif"},{"order_asce":"26","cat_id":"22","cat_name":"Dussehra Gif","cat_images":"gif_for_whatsapp_app/upload/category/Dusshera (5).gif"},{"order_asce":"27","cat_id":"1","cat_name":"Anniversary","cat_images":"gif_for_whatsapp_app/upload/category/anniversary5.gif"},{"order_asce":"27","cat_id":"27","cat_name":"Valentine Day Gif","cat_images":"gif_for_whatsapp_app/upload/category/lv_3_4.gif"},{"order_asce":"29","cat_id":"29","cat_name":"April Fool","cat_images":"gif_for_whatsapp_app/upload/category/april (2).gif"},{"order_asce":"30","cat_id":"18","cat_name":"15 August","cat_images":"gif_for_whatsapp_app/upload/category/g16.gif"},{"order_asce":"30","cat_id":"30","cat_name":"Autumn","cat_images":"gif_for_whatsapp_app/upload/category/autumn (3).gif"},{"order_asce":"32","cat_id":"4","cat_name":"Congratulations","cat_images":"gif_for_whatsapp_app/upload/category/congrats5.gif"},{"order_asce":"32","cat_id":"32","cat_name":"Chocolate Day","cat_images":"gif_for_whatsapp_app/upload/category/chocolateday (6).gif"},{"order_asce":"33","cat_id":"10","cat_name":"Thank You","cat_images":"gif_for_whatsapp_app/upload/category/thanx9.gif"},{"order_asce":"33","cat_id":"33","cat_name":"Eid Mubarak","cat_images":"gif_for_whatsapp_app/upload/category/Eid (3).gif"},{"order_asce":"34","cat_id":"34","cat_name":"Flower","cat_images":"gif_for_whatsapp_app/upload/category/Flower (3).gif"},{"order_asce":"35","cat_id":"12","cat_name":"Welcome","cat_images":"gif_for_whatsapp_app/upload/category/welcome7.gif"},{"order_asce":"35","cat_id":"35","cat_name":"Friendship Day","cat_images":"gif_for_whatsapp_app/upload/category/Friendshipday (3).gif"},{"order_asce":"36","cat_id":"14","cat_name":"Easter","cat_images":"gif_for_whatsapp_app/upload/category/easter10.gif"},{"order_asce":"36","cat_id":"36","cat_name":"Bakri Eid","cat_images":"gif_for_whatsapp_app/upload/category/bakri_eid (30).gif"},{"order_asce":"36","cat_id":"50","cat_name":"26 January ","cat_images":"gif_for_whatsapp_app/upload/category/republicday (18).gif"},{"order_asce":"37","cat_id":"16","cat_name":"Mothers Day GIF","cat_images":"gif_for_whatsapp_app/upload/category/mother_day_gif11.gif"},{"order_asce":"37","cat_id":"23","cat_name":"Christmas Gif","cat_images":"gif_for_whatsapp_app/upload/category/ch_2_7.gif"},{"order_asce":"38","cat_id":"13","cat_name":"Holi","cat_images":"gif_for_whatsapp_app/upload/category/holi1.gif"},{"order_asce":"38","cat_id":"38","cat_name":"Lord Rama","cat_images":"gif_for_whatsapp_app/upload/category/rama (6).gif"},{"order_asce":"39","cat_id":"21","cat_name":"Navratri Gif","cat_images":"gif_for_whatsapp_app/upload/category/navratri (14).gif"},{"order_asce":"39","cat_id":"39","cat_name":"Mataji","cat_images":"gif_for_whatsapp_app/upload/category/maraji (2).gif"},{"order_asce":"40","cat_id":"40","cat_name":"New Year","cat_images":"gif_for_whatsapp_app/upload/category/newyear (9).gif"},{"order_asce":"41","cat_id":"41","cat_name":"Nowruz","cat_images":"gif_for_whatsapp_app/upload/category/nowruz (5).gif"},{"order_asce":"42","cat_id":"42","cat_name":"Onam","cat_images":"gif_for_whatsapp_app/upload/category/onam (6).gif"},{"order_asce":"44","cat_id":"44","cat_name":"Propose Day","cat_images":"gif_for_whatsapp_app/upload/category/proposeday (4).gif"},{"order_asce":"45","cat_id":"45","cat_name":"Rain","cat_images":"gif_for_whatsapp_app/upload/category/rain (4).gif"},{"order_asce":"46","cat_id":"46","cat_name":"Rose","cat_images":"gif_for_whatsapp_app/upload/category/rose (4).gif"},{"order_asce":"47","cat_id":"47","cat_name":"Saint Patrick's Day","cat_images":"gif_for_whatsapp_app/upload/category/st_patricks (4).gif"},{"order_asce":"48","cat_id":"48","cat_name":"Makarsankratri","cat_images":"gif_for_whatsapp_app/upload/category/utrayan (4).gif"},{"order_asce":"49","cat_id":"49","cat_name":"Wedding","cat_images":"gif_for_whatsapp_app/upload/category/wedding (7).gif"},{"order_asce":"50","cat_id":"31","cat_name":"Bhai Dooj","cat_images":"gif_for_whatsapp_app/upload/category/Bhai_Dooj (5).gif"},{"order_asce":"51","cat_id":"28","cat_name":"Diwali Greetings","cat_images":"gif_for_whatsapp_app/upload/category/diwali_gif8.gif"},{"order_asce":"52","cat_id":"37","cat_name":"Dhan Teras","cat_images":"gif_for_whatsapp_app/upload/category/Dhan_teras (8).gif"}]
     */

    private int status;
    private List<DataBean> data;

    public static GifCategories objectFromData(String str) {

        return new Gson().fromJson(str, GifCategories.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_asce : 0
         * cat_id : 51
         * cat_name : Get Well Soon
         * cat_images : gif_for_whatsapp_app/upload/category/2857-2018-08-16.gif
         */

        private String order_asce;
        private String cat_id;
        private String cat_name;
        private String cat_images;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getOrder_asce() {
            return order_asce;
        }

        public void setOrder_asce(String order_asce) {
            this.order_asce = order_asce;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getCat_images() {
            return cat_images;
        }

        public void setCat_images(String cat_images) {
            this.cat_images = cat_images;
        }
    }
}
