package com.example.splashscreenwithlogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PizzaTypeAdapter extends RecyclerView.Adapter<PizzaTypeAdapter.ProductViewHolder> {

    private Context mCtx;
    private List <PizzaType> productList;

    public PizzaTypeAdapter(Context mCtx, List <PizzaType> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.pizza_type_layout, null,true);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final PizzaType pizzaType = productList.get(position);

        //loading the image with glide library

        //loading the image
        Glide.with(mCtx)
                .asBitmap()
                .load(pizzaType.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(pizzaType.getTitle());
        holder.textViewShortDesc.setText(pizzaType.getShortdesc());
        holder.textViewPrice.setText("₹"+String.valueOf(pizzaType.getPrice()));
        holder.pizzaType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = pizzaType.getTitle();
                String imageUrl = pizzaType.getImage();
                int id = pizzaType.getId();
                int price = pizzaType.getPrice();
                String desc = pizzaType.getShortdesc();
                Intent i = new Intent(mCtx.getApplicationContext(), gallery.class);
                i.putExtra("image",imageUrl);
                i.putExtra("title",title);
                i.putExtra("price",String.valueOf(price));
                i.putExtra("desc",desc);
                i.putExtra("id",String.valueOf(id));
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewPrice;
        ImageView imageView;
        CardView pizzaType;


        public ProductViewHolder(View itemView) {
            super(itemView);
            pizzaType = itemView.findViewById(R.id.pizzaType);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}