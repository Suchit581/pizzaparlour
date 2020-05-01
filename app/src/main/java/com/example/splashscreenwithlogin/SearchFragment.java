package com.example.splashscreenwithlogin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    SearchAdapter adapter;
    //a list to store all the products
    ArrayList<PizzaType> productList;

    //the recyclerview
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    EditText editText;
    public SearchFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadProducts();

        productList = new ArrayList<PizzaType>();
        editText=v.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                filter(s.toString());
            }
        });
        return v;
    }
    private void filter(String text) {
        ArrayList<PizzaType> filteredList = new ArrayList<>();

        for (PizzaType item : productList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                String image = Constants.URL_IMAGE + product.getString("image");
                                //adding the product to product list
                                productList.add(new PizzaType(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("desc"),
                                        product.getInt("price"),
                                        image
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            adapter = new SearchAdapter(getActivity(), productList);
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
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
