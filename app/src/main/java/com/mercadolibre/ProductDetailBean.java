package com.mercadolibre;

import java.util.List;

public class ProductDetailBean {
    public String title;
    public String price;
    public String available_quantity;
    public String sold_quantity;
    List<picture> pictures;

    public class picture{
        public String url;
    }
}
