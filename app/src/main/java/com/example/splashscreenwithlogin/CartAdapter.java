package com.example.splashscreenwithlogin;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductViewHolder> {

    private Context mCtx;
    private ArrayList <Cart> cartList;
    private static final String URL_DELETE = Constants.URL_CART_DELETE;
    private static final String URL_UPDATE = Constants.URL_CART_UPDATE;

    public CartAdapter(Context mCtx, ArrayList <Cart> cartList) {
        this.mCtx = mCtx;
        this.cartList = cartList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout_cart, null,true);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final Cart cart = cartList.get(position);

        String s = Constants.customePicre;
        Glide.with(mCtx)
                .asBitmap()
                .load(cart.getImage())
                .into(holder.imageView);
        holder.textViewTitle.setText(cart.getTitle());
        String cartTitle = cart.getTitle();
        if (cartTitle.equals("Custom Pizza")){
            holder.price = Integer.parseInt(s);
        }else{
            holder.price = cart.getOriginal_pizza_price();
        }
        holder.quantity.setNumber(String.valueOf(cart.getQuantity()));
        holder.textPriceTag.setText(String.valueOf(cart.getPrice()));
        holder.quantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ans;
                int q= Integer.parseInt(holder.quantity.getNumber());
                if (q == 1){
                    holder.textPriceTag.setText(String.valueOf(holder.price));
                }else{
                    ans = holder.price;
                    ans = ans * q;
                    holder.textPriceTag.setText(String.valueOf(ans));
                }
                update(cart.getId(),holder.textPriceTag.getText().toString(),q);


            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textPriceTag;
        ImageView imageView;
        CardView parent_layout;
        RelativeLayout viewBackground;
        ElegantNumberButton quantity;
        int price;

        public ProductViewHolder(View itemView) {
            super(itemView);
            viewBackground = itemView.findViewById(R.id.view_background);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageView);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            textPriceTag = itemView.findViewById(R.id.textViewPrice);
            quantity = itemView.findViewById(R.id.myElegantButton);
            quantity.setRange(1,10);
        }
    }
    public void removeItem(int position) {
        final Cart cart = cartList.get(position);
        int id= cart.getId();
        delete(id);
        cartList.remove(position);
        getItemCount();
        notifyItemRemoved(position);
    }

    public void delete(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String ,String> params=new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);
    }
    public void update(final int id, final String price, final int quantity){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String ,String> params=new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("price", String.valueOf(price));
                params.put("quantity", String.valueOf(quantity));
                return params;
            }
        };
        RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);
    }
}