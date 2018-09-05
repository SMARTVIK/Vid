package com.hunter.videostatus.model;

import com.google.gson.Gson;

import java.util.List;

public class CategoryModel {


    /**
     * success : 1
     * data : [{"category_id":"24","category_name":"Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/24video status.png","category_order":"1","category_status":"Active"},{"category_id":"25","category_name":"Love Videos","category_image":"http://vidstatus.in/vidstatus/category_images/25Love Videos.png","category_order":"2","category_status":"Active"},{"category_id":"11","category_name":"Funny Videos","category_image":"http://vidstatus.in/vidstatus/category_images/11funny Videos.png","category_order":"3","category_status":"Active"},{"category_id":"14","category_name":"Comedy Videos","category_image":"http://vidstatus.in/vidstatus/category_images/14Comedy Videos.png","category_order":"4","category_status":"Active"},{"category_id":"16","category_name":"Viral Videos","category_image":"http://vidstatus.in/vidstatus/category_images/16Viral Videos.png","category_order":"5","category_status":"Active"},{"category_id":"13","category_name":"Interesting Videos","category_image":"http://vidstatus.in/vidstatus/category_images/13Interesting Videos.png","category_order":"6","category_status":"Active"},{"category_id":"17","category_name":"Social Media Videos","category_image":"http://vidstatus.in/vidstatus/category_images/17Social Media Videos.png","category_order":"7","category_status":"Active"},{"category_id":"18","category_name":"Hot Videos","category_image":"http://vidstatus.in/vidstatus/category_images/18Hot Videos.jpg","category_order":"8","category_status":"Active"},{"category_id":"54","category_name":"English Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/54English Video Status.jpg","category_order":"9","category_status":"Active"},{"category_id":"20","category_name":"Bollywood Videos","category_image":"http://vidstatus.in/vidstatus/category_images/20Bollywood Videos1.png","category_order":"10","category_status":"Active"},{"category_id":"21","category_name":"Motivational Videos","category_image":"http://vidstatus.in/vidstatus/category_images/21Motivational Videos.jpg","category_order":"11","category_status":"Active"},{"category_id":"22","category_name":"Sports Videos","category_image":"http://vidstatus.in/vidstatus/category_images/22Sports Videos1.png","category_order":"12","category_status":"Active"},{"category_id":"23","category_name":"Technology Videos","category_image":"http://vidstatus.in/vidstatus/category_images/23Technology Videos.png","category_order":"13","category_status":"Active"},{"category_id":"26","category_name":"Inspirational Videos","category_image":"http://vidstatus.in/vidstatus/category_images/26Uncensored Videos.png","category_order":"14","category_status":"Active"},{"category_id":"49","category_name":"Punjabi Status Videos","category_image":"http://vidstatus.in/vidstatus/category_images/49Punjabi icom.png","category_order":"15","category_status":"Active"},{"category_id":"50","category_name":"Bengali Status Videos","category_image":"http://vidstatus.in/vidstatus/category_images/50Bangali.png","category_order":"16","category_status":"Active"},{"category_id":"55","category_name":"Dialogue Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/55Dialogue.png","category_order":"17","category_status":"Active"},{"category_id":"53","category_name":"Festival Status Video","category_image":"http://vidstatus.in/vidstatus/category_images/53814058_festival_512x512.png","category_order":"18","category_status":"Active"},{"category_id":"58","category_name":"Marathi Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/58Marathi icon.png","category_order":"19","category_status":"Active"},{"category_id":"60","category_name":"Old is Gold Status","category_image":"http://vidstatus.in/vidstatus/category_images/60old is gold.png","category_order":"19","category_status":"Active"},{"category_id":"59","category_name":"Tamil Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/59Tanil.png","category_order":"20","category_status":"Active"},{"category_id":"61","category_name":"Gujarati Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/61gujrati.jpg","category_order":"22","category_status":"Active"},{"category_id":"62","category_name":"Telugu Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/62telugu.png","category_order":"23","category_status":"Active"},{"category_id":"63","category_name":"Kannada Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/63Kannada2.png","category_order":"24","category_status":"Active"},{"category_id":"19","category_name":"Double Meaning Videos","category_image":"http://vidstatus.in/vidstatus/category_images/19Double Meaning Videos.jpg","category_order":"25","category_status":"Active"},{"category_id":"72","category_name":"Friendship Status","category_image":"http://vidstatus.in/vidstatus/category_images/72Friendship.png","category_order":"26","category_status":"Active"},{"category_id":"81","category_name":"Portrait Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/81Portrait.png","category_order":"27","category_status":"Active"},{"category_id":"87","category_name":"Birthday Wishes Video","category_image":"http://vidstatus.in/vidstatus/category_images/87Birthday-Video.png","category_order":"28","category_status":"Active"},{"category_id":"69","category_name":"Islamic Video Status","category_image":"http://vidstatus.in/vidstatus/category_images/69ramzan.png","category_order":"29","category_status":"Active"}]
     */

    private String success;
    private List<DataBean> data;

    public static CategoryModel objectFromData(String str) {

        return new Gson().fromJson(str, CategoryModel.class);
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * category_id : 24
         * category_name : Video Status
         * category_image : http://vidstatus.in/vidstatus/category_images/24video status.png
         * category_order : 1
         * category_status : Active
         */

        private String category_id;
        private String category_name;
        private String category_image;
        private String category_order;
        private String category_status;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_image() {
            return category_image;
        }

        public void setCategory_image(String category_image) {
            this.category_image = category_image;
        }

        public String getCategory_order() {
            return category_order;
        }

        public void setCategory_order(String category_order) {
            this.category_order = category_order;
        }

        public String getCategory_status() {
            return category_status;
        }

        public void setCategory_status(String category_status) {
            this.category_status = category_status;
        }
    }
}
