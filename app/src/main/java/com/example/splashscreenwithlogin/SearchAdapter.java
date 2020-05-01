package com.example.splashscreenwithlogin;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductViewHolder>{

    private Context mCtx;
    private ArrayList <PizzaType> productList;


    public SearchAdapter(FragmentActivity mCtx, ArrayList <PizzaType> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null,true);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final PizzaType product = productList.get(position);


        //holder.textViewTitle.setText((CharSequence) productList.get(position));
        Glide.with(mCtx)
                .asBitmap()
                .load(product.getImage())
                .into(holder.imageView);
        holder.textViewTitle.setText(product.getTitle());

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titles = product.getTitle();
                if (titles.equals("custom pizza")){
                    Intent i = new Intent(mCtx.getApplicationContext(), CustomPizzaActivity.class);
                    mCtx.getApplicationContext().startActivity(i);
                }
                else{

                    int pos1 = position +1;
                    String pos = String.valueOf(pos1);
                    String title = product.getTitle();
                    String imageUrl = product.getImage();
                    int id = product.getId();
                    int price = product.getPrice();
                    String desc = product.getShortdesc();
                    Intent i = new Intent(mCtx.getApplicationContext(), gallery.class);
                    i.putExtra("image",imageUrl);
                    i.putExtra("title",title);
                    i.putExtra("price",String.valueOf(price));
                    i.putExtra("desc",desc);
                    i.putExtra("id",String.valueOf(id));
                    mCtx.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;
        CardView parent_layout;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
    public void filterList(ArrayList<PizzaType> filteredList) {
        productList= filteredList;
        getItemCount();
        notifyDataSetChanged();
    }

}