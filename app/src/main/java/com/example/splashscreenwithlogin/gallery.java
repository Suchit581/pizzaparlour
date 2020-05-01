package com.example.splashscreenwithlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class gallery extends AppCompatActivity {
    private static final String URL_INSERT = Constants.URL_CART_INSERT;
    String ImageUrl ;
    String title ;
    String id ;
    String price ;
    String desc ;
    String user_id = SharedPrefManager.getInstance(this).getUserId();
    String qunatity = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getIntentData();
    }
    private  void getIntentData(){
        if (getIntent().hasExtra("image")
            && getIntent().hasExtra("title")
            && getIntent().hasExtra("id")
            && getIntent().hasExtra("price")
            && getIntent().hasExtra("desc")){
                ImageUrl =  getIntent().getStringExtra("image");
                title =  getIntent().getStringExtra("title");
                id = getIntent().getStringExtra("id");
                price = getIntent().getStringExtra("price");
                desc = getIntent().getStringExtra("desc");
                setImage(ImageUrl,title,price,desc);
        }
    }
    private  void setImage(final String imageUrl, final String title, final String price, String desc){
        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);

        TextView titles = findViewById(R.id.image_description);
        titles.setText(title);

        TextView prices = findViewById(R.id.price);
        prices.setText(price);

        TextView description = findViewById(R.id.description);
        description.setText(desc);

        Button addCart = findViewById(R.id.addToCart);
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ImageUrl;
                String s1 = s.substring(s.indexOf("u"));
                s1.trim();
                insertCart(user_id,title,qunatity,price,s1,id);
                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        });
    }

    public void insertCart(final String user_id, final String title, final String qunatity, final String price, final String imageUrl, final String pizza_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            Toast.makeText(gallery.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
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
                params.put("user_id",user_id );
                params.put("title",title );
                params.put("quantity",qunatity);
                params.put("price",price );
                params.put("image",imageUrl );
                params.put("pizza_id",pizza_id );
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
