package com.hunter.videostatus.gifandvideos;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
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
    Call<MainPojoGIF> getGifById(@Query("cat_id") String str, @Query("page") String str2, @Query("limit") int i);

    @GET("search?")
    Call<MailSearch> getSearchData(@Query("q") String str, @Query("api_key") String str2, @Query("limit") int i, @Query("offset") int i2);

    @GET("moreapp_list.php")
    Call<MainPojo> getTopRatedMovies();
}