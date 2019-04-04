package com.mercadolibre;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    ProductBean[] products;
    Context context;

    public static final String ITEM_ID = "com.mercadolibre.ITEM_ID";

    public ListAdapter(ProductBean[] products, Context context){
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.title.setText(products[position].title);
        String[] parts = products[position].price.split("\\.");
        String number = parts.length == 0 ? String.format("%,d", Integer.parseInt(products[position].price)) : String.format("%,d", Integer.parseInt(parts[0]));
        number = number.replace(",",".");
        String decimal = parts.length == 2 ? parts[1] : "";

        final int pos = position;

        holder.price.setText("$ "+number);
        holder.decimal.setText(decimal);
        if(!products[position].sold_quantity.equals("0"))
            holder.sold_quantity.setText(products[position].sold_quantity+" vendidos");

        Glide
                .with(context)
                .load(products[position].thumbnail)
                .into(holder.image);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(ITEM_ID, products[pos].id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public static class ListHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView price;
        public TextView decimal;
        public TextView sold_quantity;
        public ImageView image;
        public View container;
        public ListHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            decimal = itemView.findViewById(R.id.decimal);
            sold_quantity = itemView.findViewById(R.id.sold_quantity);
            image = itemView.findViewById(R.id.image);
            container = itemView.findViewById(R.id.container);
        }
    }
}
