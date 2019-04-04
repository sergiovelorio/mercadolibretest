package com.mercadolibre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public ProductDetailBean productDetailBean;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra(ListAdapter.ITEM_ID);
        viewPager = findViewById(R.id.viewpager);



        ProductHandler ph = new ProductHandler(DetailActivity.this);
        ph.productDetail(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String body = response.body().string();
                    JSONObject jsonObject = new JSONObject(body);
                    productDetailBean = new Gson().fromJson(jsonObject.toString(), ProductDetailBean.class);

                    viewPager.setAdapter(new PageAdapter(DetailActivity.this, productDetailBean.pictures));

                    String[] parts = productDetailBean.price.split("\\.");
                    String number = parts.length == 0 ? String.format("%,d", Integer.parseInt(productDetailBean.price)) : String.format("%,d", Integer.parseInt(parts[0]));
                    number = number.replace(",",".");
                    String decimal = parts.length == 2 ? parts[1] : "";

                    ((TextView)findViewById(R.id.title)).setText(productDetailBean.title);
                    ((TextView)findViewById(R.id.price)).setText("$ "+number);
                    ((TextView)findViewById(R.id.decimal)).setText(decimal);
                    if(!productDetailBean.sold_quantity.equals("0"))
                        ((TextView)findViewById(R.id.sold_quantity)).setText(productDetailBean.sold_quantity+" vendidos");

                    if(productDetailBean.pictures.size() > 1){
                        findViewById(R.id.prev).setVisibility(View.VISIBLE);
                        findViewById(R.id.next).setVisibility(View.VISIBLE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        },itemId);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.finish();
            }
        });

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() - 1 >= 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() + 1 <= productDetailBean.pictures.size()-1)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
    }

}
