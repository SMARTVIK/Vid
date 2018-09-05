package com.hunter.videostatus.vidstatus;
import com.hunter.videostatus.gifandvideos.GifCategories;
import com.hunter.videostatus.gifandvideos.MailSearch;
import com.hunter.videostatus.gifandvideos.MainCategories;
import com.hunter.videostatus.gifandvideos.MainData;
import com.hunter.videostatus.gifandvideos.MainNotification;
import com.hunter.videostatus.gifandvideos.MainPojo;
import com.hunter.videostatus.gifandvideos.MainPojoGIF;
import com.hunter.videostatus.model.CategoryModel;
import com.hunter.videostatus.model.Status;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    /*All video status*/

    @GET("api.php?req=statuslist&statustype=videostatus")
    Call<Status> getLatestVideoStatus(@Query("page") int page); //Get latest video status

    @GET("api.php?req=popularstatus&statustype=videostatus")
    Call<Status> getTrendingVideoStatus(@Query("page") int page); //Get Trending Video Status

    @GET("api.php?req=statuslist&statustype=videostatus")
    Call<Status> getTrendingVideoStatusByCat(@Query("page") int page, @Query("category_id") String category_id);

    @GET("api.php?req=cactegory&statustype=videostatus")
    Call<CategoryModel> getVideoCatigories(); //All Video Categories

    @GET("api.php?req=cactegory&statustype=textstatus")
    Call<CategoryModel> getTextCatigories();

    @GET("api.php?req=cactegory&statustype=imagestatus")
    Call<CategoryModel> getImageCatigories();

    @GET("api.php?req=statuslist&statustype=imagestatus")
    Call<Status> getLatestImageStatus(@Query("page") int page);

    @GET("api.php?req=statuslist&statustype=textstatus")
    Call<Status> getLatestTextStatus(@Query("page") int page);

    @GET("api.php?req=popularstatus&statustype=imagestatus")
    Call<Status> getTrendingImageStatus(@Query("page") int page);

    @GET("api.php?req=popularstatus&statustype=textstatus")
    Call<Status> getTrendingTextStatus(@Query("page") int page);

    @GET("api.php?req=statuslist&statustype=imagestatus")
    Call<Status> getTrendingImageStatusByCat(@Query("page") int page, @Query("category_id") String category_id);

    @GET("api.php?req=statuslist&statustype=textstatus")
    Call<Status> getTrendingTextStatusByCat(@Query("page") int page, @Query("category_id") String category_id);

    @GET("api.php?req=statuslike")
    Call<Status> setStatusLike(@Query("page") int page, @Query("category_id") String category_id);

    @GET("api.php?req=searchstatus&statustype=videostatus")
    Call<Status> getSearchResults(@Query("tag") String tag);



    //gif apis
    @GET("get_status_video_category.php")
    Call<MainCategories> getCategories();

    @GET("get_status_video_popular.php")
    Call<MainData> getPopularVideos(@Query("limit") int i, @Query("page") int i2);

    @GET("get_status_video_by_cat.php")
    Call<MainData> getVideoByCat(@Query("cat_id") String str, @Query("limit") int i, @Query("page") int i2);

    @FormUrlEncoded
    @POST("gif_for_chat/noti/RegisterDevice.php")
    Call<MainNotification> sendToken(@Field("device_id") String str, @Field("token") String str2);

    @GET("get_gif_for_chat_category.php")
    Call<GifCategories> getGIFcategory();

    @GET("get_gif_for_chat_by_cat.php")
    Call<com.hunter.videostatus.gifandvideos.MainPojoGIF> getGifById(@Query("cat_id") String str, @Query("page") String str2, @Query("limit") int i);

    @GET("search?")
    Call<com.hunter.videostatus.gifandvideos.MailSearch> getSearchData(@Query("q") String str, @Query("api_key") String str2, @Query("limit") int i, @Query("offset") int i2);

    @GET("moreapp_list.php")
    Call<com.hunter.videostatus.gifandvideos.MainPojo> getTopRatedMovies();


//    http://mirchikart.com/videostatus/api/api.php?req=statuslist
//    http://mirchikart.com/videostatus/api/api.php?req=cactegory
}