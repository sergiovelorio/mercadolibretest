package com.mercadolibre;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Products {
    @Headers({"Accept:application/json","Content-Type:application/json"})
    @GET("sites/MLU/search")
    Call<ResponseBody> productList(@Query("q") String query, @Query("limit") int limit, @Query("offset") int offset);

    @GET("items/{itemId}")
    Call<ResponseBody> productDetail(@Path("itemId") String itemId);
}
