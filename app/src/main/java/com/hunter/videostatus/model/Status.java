package com.hunter.videostatus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Status implements Parcelable{
    /**
     * page : 1
     * total_rec : 3569
     * limit : 10
     * success : 1
     * data : [{"id":"20298","name":"Dil Meri Na Sune Atif Aslam Awesome status","imgurl":"http://vidstatus.in/vidstatus/status/202/20298Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20298Dil Meri Na Sune Atif Aslam Awesome status.mp4","tag":"love","textstatus":"","downloads":"3270","view":"9426","share":"1878","status":"1","datetime":"2018-07-29 16:22:39","like":"279","dislike":"23"},{"id":"20297","name":"Buzz - Badshah - Remix Status","imgurl":"http://vidstatus.in/vidstatus/status/202/20297Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20297Buzz - Badshah - Remix Status.mp4","tag":"love","textstatus":"","downloads":"979","view":"5392","share":"478","status":"1","datetime":"2018-07-29 16:21:25","like":"119","dislike":"16"},{"id":"20296","name":"Waqt ki thi Humse Kuch Narazgi - Awesome Status","imgurl":"http://vidstatus.in/vidstatus/status/202/20296Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20296Waqt ki thi Humse Kuch Narazgi - Awesome Status.mp4","tag":"love","textstatus":"","downloads":"1038","view":"6031","share":"555","status":"1","datetime":"2018-07-29 16:19:20","like":"95","dislike":"10"},{"id":"20295","name":"Raaz Aankhein Teri","imgurl":"http://vidstatus.in/vidstatus/status/202/20295Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20295Raaz Aankhein Teri.mp4","tag":"love","textstatus":"","downloads":"1440","view":"5543","share":"677","status":"1","datetime":"2018-07-29 16:15:57","like":"115","dislike":"7"},{"id":"20294","name":"Sab Kuch Bhula Diya","imgurl":"http://vidstatus.in/vidstatus/status/202/20294Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20294Sab Kuch Bhula Diya.mp4","tag":"Sad","textstatus":"","downloads":"1805","view":"5794","share":"1055","status":"1","datetime":"2018-07-29 16:13:52","like":"114","dislike":"16"},{"id":"20293","name":"I Feel Love","imgurl":"http://vidstatus.in/vidstatus/status/202/20293Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20293I Feel Love.mp4","tag":"love","textstatus":"","downloads":"797","view":"4234","share":"338","status":"1","datetime":"2018-07-29 16:11:53","like":"78","dislike":"5"},{"id":"20292","name":"Tere Mere Hoton Pe","imgurl":"http://vidstatus.in/vidstatus/status/202/20292Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20292Tere Mere Hoton Pe.mp4","tag":"love","textstatus":"","downloads":"1194","view":"4745","share":"805","status":"1","datetime":"2018-07-29 16:09:44","like":"83","dislike":"9"},{"id":"20291","name":"Tamil Sad Status","imgurl":"http://vidstatus.in/vidstatus/status/202/20291Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20291Tamil Sad Status.mp4","tag":"Sad","textstatus":"","downloads":"89","view":"1212","share":"53","status":"1","datetime":"2018-07-29 16:06:43","like":"13","dislike":"1"},{"id":"20290","name":"Cute Romantic Status","imgurl":"http://vidstatus.in/vidstatus/status/202/20290Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20290Cute Romantic Status.mp4","tag":"love","textstatus":"","downloads":"1020","view":"5878","share":"462","status":"1","datetime":"2018-07-29 16:04:24","like":"113","dislike":"10"},{"id":"20236","name":"Dedu Dil Mera Dedu Jaan Awesome Status","imgurl":"http://vidstatus.in/vidstatus/status/202/20236Screenshot_1.jpg","videourl":"http://vidstatus.in/vidstatus/status/202/20236Dedu Dil Mera Dedu Jaan Awesome Status.mp4","tag":"love","textstatus":"","downloads":"4094","view":"15756","share":"2719","status":"1","datetime":"2018-07-28 15:46:37","like":"398","dislike":"50"}]
     */

    private int page;
    private String total_rec;
    private int limit;
    private String success;
    private ArrayList<DataBean> data;

    protected Status(Parcel in) {
        page = in.readInt();
        total_rec = in.readString();
        limit = in.readInt();
        success = in.readString();
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public static Status objectFromData(String str) {

        return new Gson().fromJson(str, Status.class);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTotal_rec() {
        return total_rec;
    }

    public void setTotal_rec(String total_rec) {
        this.total_rec = total_rec;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeString(total_rec);
        dest.writeInt(limit);
        dest.writeString(success);
    }

    public static class DataBean implements Parcelable{
        /**
         * id : 20298
         * name : Dil Meri Na Sune Atif Aslam Awesome status
         * imgurl : http://vidstatus.in/vidstatus/status/202/20298Screenshot_1.jpg
         * videourl : http://vidstatus.in/vidstatus/status/202/20298Dil Meri Na Sune Atif Aslam Awesome status.mp4
         * tag : love
         * textstatus :
         * downloads : 3270
         * view : 9426
         * share : 1878
         * status : 1
         * datetime : 2018-07-29 16:22:39
         * like : 279
         * dislike : 23
         */

        private String id;
        private String name;
        private String imgurl;
        private String videourl;
        private String tag;
        private String textstatus;
        private String downloads;
        private String view;
        private String share;
        private String status;
        private String datetime;
        private String like;
        private String dislike;

        protected DataBean(Parcel in) {
            id = in.readString();
            name = in.readString();
            imgurl = in.readString();
            videourl = in.readString();
            tag = in.readString();
            textstatus = in.readString();
            downloads = in.readString();
            view = in.readString();
            share = in.readString();
            status = in.readString();
            datetime = in.readString();
            like = in.readString();
            dislike = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getVideourl() {
            return videourl;
        }

        public void setVideourl(String videourl) {
            this.videourl = videourl;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTextstatus() {
            return textstatus;
        }

        public void setTextstatus(String textstatus) {
            this.textstatus = textstatus;
        }

        public String getDownloads() {
            return downloads;
        }

        public void setDownloads(String downloads) {
            this.downloads = downloads;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getDislike() {
            return dislike;
        }

        public void setDislike(String dislike) {
            this.dislike = dislike;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(imgurl);
            dest.writeString(videourl);
            dest.writeString(tag);
            dest.writeString(textstatus);
            dest.writeString(downloads);
            dest.writeString(view);
            dest.writeString(share);
            dest.writeString(status);
            dest.writeString(datetime);
            dest.writeString(like);
            dest.writeString(dislike);
        }
    }
}
