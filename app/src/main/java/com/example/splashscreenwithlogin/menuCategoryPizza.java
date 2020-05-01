package com.example.splashscreenwithlogin;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class menuCategoryPizza extends Fragment {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = Constants.URL_PRODUCTS_PIZ;
    private static final String URL_IMAGE = Constants.URL_IMAGE;
    BottomNavigationView mMainNav;

    //a list to store all the products
    List<PizzaType> productList;

    RecyclerView PizzaCategory;

    String position;
    View v;

    public menuCategoryPizza() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        PizzaCategory = getView().findViewById(R.id.categoryPizza);
        PizzaCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        position = "3";
        //initializing the productlist
        productList = new ArrayList<PizzaType>();

        userLogin();
        return inflater.inflate(R.layout.fragment_menu_category_pizza, container, false);
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
                    PizzaTypeAdapter adapter = new PizzaTypeAdapter(getActivity(), productList);
                    PizzaCategory.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
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
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
