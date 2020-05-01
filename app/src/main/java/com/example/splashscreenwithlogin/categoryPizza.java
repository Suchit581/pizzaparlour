package com.example.splashscreenwithlogin;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class categoryPizza extends AppCompatActivity {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = Constants.URL_PRODUCTS_PIZ;
    private static final String URL_IMAGE = Constants.URL_IMAGE;
    BottomNavigationView mMainNav;

    //a list to store all the products
    List <PizzaType> productList;

    RecyclerView PizzaCategory;

    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_pizza);

        PizzaCategory = findViewById(R.id.categoryPizza);
        PizzaCategory.setLayoutManager(new LinearLayoutManager(this));

        position = getIntent().getStringExtra("position");
        //initializing the productlist
        productList = new ArrayList <PizzaType>();
        //this method will fetch and parse json
        //to display it in recyclerview
        userLogin();
    }

    private void userLogin(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(categoryPizza.this, response, Toast.LENGTH_SHORT).show();
//                converting the string to json array object
                JSONArray array = null;
                try {
                    array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject product = array.getJSONObject(i);
                        String image = URL_IMAGE + product.getString("image");
                        productList.add(new PizzaType(
                                product.getInt("id"),
                                product.getString("title"),
                                product.getString("desc"),
                                product.getInt("price"),
                                image
                        ));
                    }
                    //creating adapter object and setting it to recyclerview
                    PizzaTypeAdapter adapter = new PizzaTypeAdapter(categoryPizza.this, productList);
                    PizzaCategory.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String ,String> params=new HashMap<>();
                params.put("pos", position);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}
