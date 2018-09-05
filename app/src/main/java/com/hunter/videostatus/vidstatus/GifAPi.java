package com.hunter.videostatus.vidstatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GifAPi {

    @GET("get_gif_for_chat_category.php")
    Call<MainPojoCategories> getGIFcategory();

    @GET("get_gif_for_chat_by_cat.php")
    Call<MainPojoGIF> getGifById(@Query("cat_id") String str, @Query("page") String str2, @Query("limit") int i);

    @GET("search?")
    Call<MailSearch> getSearchData(@Query("q") String str, @Query("api_key") String str2, @Query("limit") int i, @Query("offset") int i2);

    @GET("moreapp_list.php")
    Call<MainPojo> getTopRatedMovies();

}
