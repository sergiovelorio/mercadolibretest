package com.mercadolibre;

import android.support.v7.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductHandler extends RetroBase {
    private Products routes;

    public ProductHandler(AppCompatActivity appCompatActivity){
        super(appCompatActivity);
        routes = retroft.create(Products.class);
    }

    public void productSearch(Callback<ResponseBody> callBack, String query, int limit, int offset){
        Call<ResponseBody> call = routes.productList(query, limit, offset);
        call.enqueue(callBack);
    }

    public void productDetail(Callback<ResponseBody> callBack, String itemId){
        Call<ResponseBody> call = routes.productDetail(itemId);
        call.enqueue(callBack);
    }
}
