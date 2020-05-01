package com.example.splashscreenwithlogin;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

public class CartFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = Constants.URL_CART_SELECT;
    private static final String URL_TOTAL_PRICE = Constants.URL_TOTALPRICE;
    private static final String URL_IMAGE = Constants.URL_IMAGE;
    String user_id = SharedPrefManager.getInstance(getContext()).getUserId();
    CartAdapter adapter;
    Button button;
    //a list to store all the products
    List<Cart> cartList;

    //the recyclerview
    RecyclerView recyclerView;
    TextView TotalPrice;
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        //getting the recyclerview from xml
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //select location

        button=v.findViewById(R.id.selectLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });
        TotalPrice = v.findViewById(R.id.TotalPrice);

        //initializing the productlist
        cartList = new ArrayList<Cart>();
//
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        loadProducts();
        totalPrice();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {}

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                totalPrice();
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);


        // total price update every 5 minute
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                totalPrice();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
        return v;
    }
    public void totalPrice(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TOTAL_PRICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject total = array.getJSONObject(0);
                            TotalPrice.setText(String.valueOf(total.getInt("totalprice")));
                            Constants.PAYTM_PRICE=String.valueOf(total.getInt("totalprice"));

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
                params.put("user_id", user_id);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
        int s = Integer.parseInt(TotalPrice.getText().toString());
        if (s == 0 ){
            button.setEnabled(false);
        }else{
            button.setEnabled(true);
        }
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                String image = URL_IMAGE + product.getString("image");
                                //adding the product to product list
                                cartList.add(new Cart(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getInt("quantity"),
                                        product.getInt("price"),
                                        image,
                                        product.getInt("original_pizza_price")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            adapter = new CartAdapter(getContext(), (ArrayList<Cart>) cartList);
                            recyclerView.setAdapter(adapter);
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
                params.put("id", user_id);
                return params;
            }
        };
        //adding our stringrequest to queue
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.ProductViewHolder) {
            adapter.removeItem(viewHolder.getAdapterPosition());
            totalPrice();
        }
    }

}
