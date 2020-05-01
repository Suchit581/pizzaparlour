package com.example.splashscreenwithlogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class CustomPizzaActivity extends AppCompatActivity {

    TextView vegTopping,nonVegTopping,price,description;
    ImageView veg_black_olive,veg_corn,veg_green_capsicum,veg_green_chilly,veg_green_olive,veg_herb,veg_mushroom,veg_onion,veg_pineapple,veg_red_capsicum,veg_red_chilly,veg_tomato,non_veg_bacon,non_veg_meat_piece,non_veg_salami,non_veg_saussages,non_veg_slices_of_meat,BackImage;
    GridLayout vegToppingGrid,nonVegToppingGrid;
    List<String> vegToppingList;
    List<String> NonVegToppingList;
    List<String> vegToppingListChecked;
    Button addTocartButton;
    int VegtrueValue = 0;
    int NonVegtrueValue = 0;
    int VegToppingPrice = 50;
    int NonVegToppingPrice = 65;
    String desc;

    private static final String URL_INSERT = Constants.URL_CART_INSERT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_pizza);
        vegToppingList = new ArrayList <String>();
        NonVegToppingList = new ArrayList <String>();
        vegToppingListChecked = new ArrayList <String>();

        init();
        listner();
        listInit();
        VegSetToggleEvent(vegToppingGrid);
        NonVegSetToggleEvent(nonVegToppingGrid);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                totalPriceTopping();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    public  void listner(){
        vegTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegTopping.setBackground(getDrawable(R.drawable.bottom_stroke_active));
                nonVegTopping.setBackground(getDrawable(R.drawable.bottom_stroke));
                vegToppingGrid.setVisibility(View.VISIBLE);
                nonVegToppingGrid.setVisibility(View.GONE);
            }
        });
        nonVegTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegTopping.setBackground(getDrawable(R.drawable.bottom_stroke));
                nonVegTopping.setBackground(getDrawable(R.drawable.bottom_stroke_active));
                nonVegToppingGrid.setVisibility(View.VISIBLE);
                vegToppingGrid.setVisibility(View.GONE);
            }
        });
        addTocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setVisibility(View.VISIBLE);
                buttonClickCardView(vegToppingGrid,nonVegToppingGrid);
            }
        });
    }

    int buttonClickedCount = 0;
    private void buttonClickCardView(GridLayout vegToppingGrid, GridLayout nonVegToppingGrid) {
        //veg
        if (buttonClickedCount == 0){
            for (int i = 0; i < vegToppingGrid.getChildCount(); i++) {
                final CardView cardView = (CardView) vegToppingGrid.getChildAt(i);
                final int finalI = i;
                if (!(cardView.getCardBackgroundColor().getDefaultColor() == -1)) {
                    vegToppingListChecked.add(vegToppingList.get(finalI));
                }
            }
            buttonClickedCount = 1;
        }
        if (buttonClickedCount == 0){
            for (int i = 0; i < nonVegToppingGrid.getChildCount(); i++) {
                final CardView cardView = (CardView) vegToppingGrid.getChildAt(i);
                final int finalI = i;
                if (!(cardView.getCardBackgroundColor().getDefaultColor() == -1)) {
                    vegToppingListChecked.add(NonVegToppingList.get(finalI));
                }
            }
            buttonClickedCount = 1;
        }
        String p = price.getText().toString();
        Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
        setDesc();
        String user_id = SharedPrefManager.getInstance(getContext()).getUserId();
        insertCart(user_id,"Custom Pizza","1",p,"uploads/custom_pizza.png","28");
        Constants.customePicre = p;
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        };
        handler.postDelayed(runnable, 1000);
    }
    public  void setDesc(){
        String d = "";
        for (int i=0;i<vegToppingListChecked.size();i++){
            if (i ==0){
                d = String.valueOf(vegToppingListChecked.get(i));
            }else{
                d = d +" ,"+String.valueOf(vegToppingListChecked.get(i));
            }
        }
        description.setText(d);
    }

    public void init()
    {
        //TextView
        vegTopping = findViewById(R.id.veg_topping);
        nonVegTopping = findViewById(R.id.non_veg_topping);
        price = findViewById(R.id.rs);
        description = findViewById(R.id.desc);

        //ImageView
        veg_black_olive = findViewById(R.id.veg_black_olive);
        veg_corn = findViewById(R.id.veg_corn);
        veg_green_capsicum = findViewById(R.id.veg_green_capsicum);
        veg_green_chilly = findViewById(R.id.veg_green_chilly);
        veg_green_olive = findViewById(R.id.veg_green_olive);
        veg_herb = findViewById(R.id.veg_herb);
        veg_mushroom = findViewById(R.id.veg_mushroom);
        veg_onion = findViewById(R.id.veg_onion);
        veg_pineapple = findViewById(R.id.veg_pineapple);
        veg_red_capsicum = findViewById(R.id.veg_red_capsicum);
        veg_red_chilly = findViewById(R.id.veg_red_chilly);
        veg_tomato = findViewById(R.id.veg_tomato);

        non_veg_bacon = findViewById(R.id.non_veg_bacon);
        non_veg_meat_piece = findViewById(R.id.non_veg_meat_piece);
        non_veg_salami = findViewById(R.id.non_veg_salami);
        non_veg_saussages = findViewById(R.id.non_veg_saussages);
        non_veg_slices_of_meat = findViewById(R.id.non_veg_slices_of_meat);

        BackImage = findViewById(R.id.BackImage);

        //gridlayout
        vegToppingGrid = findViewById(R.id.vegGrid);
        nonVegToppingGrid = findViewById(R.id.NonVegGrid);

        //Button
        addTocartButton = findViewById(R.id.AddToCartButton);
    }



    public void VegSetToggleEvent(GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        VegLoadIamge(finalI);
                        vegTrueValue();
                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        VegHideIamge(finalI);
                        vegFalseValue();
                    }

                }
            });
        }
    }

    private void NonVegSetToggleEvent(GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        NonVegLoadIamge(finalI);
                        NonvegTrueValue();
                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        NonVegHideIamge(finalI);
                        NonvegFalseValue();
                    }
                }
            });
        }
    }

    public void VegLoadIamge(int finalI){
        switch (finalI){
            case 0:
                veg_black_olive.setVisibility(View.VISIBLE);
                break;
            case 1:
                veg_corn.setVisibility(View.VISIBLE);
                break;
            case 2:
                veg_green_capsicum.setVisibility(View.VISIBLE);
                break;
            case 3:
                veg_green_chilly.setVisibility(View.VISIBLE);
                break;
            case 4:
                veg_green_olive.setVisibility(View.VISIBLE);
                break;
            case 5:
                veg_herb.setVisibility(View.VISIBLE);
                break;
            case 6:
                veg_mushroom.setVisibility(View.VISIBLE);
                break;
            case 7:
                veg_onion.setVisibility(View.VISIBLE);
                break;
            case 8:
                veg_pineapple.setVisibility(View.VISIBLE);
                break;
            case 9:
                veg_red_capsicum.setVisibility(View.VISIBLE);
                break;
            case 10:
                veg_red_chilly.setVisibility(View.VISIBLE);
                break;
            case 11:
                veg_tomato.setVisibility(View.VISIBLE);
                break;
            default:
                BackImage.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void VegHideIamge(int finalI){
        switch (finalI){
            case 0:
                veg_black_olive.setVisibility(View.GONE);
                break;
            case 1:
                veg_corn.setVisibility(View.GONE);
                break;
            case 2:
                veg_green_capsicum.setVisibility(View.GONE);
                break;
            case 3:
                veg_green_chilly.setVisibility(View.GONE);
                break;
            case 4:
                veg_green_olive.setVisibility(View.GONE);
                break;
            case 5:
                veg_herb.setVisibility(View.GONE);
                break;
            case 6:
                veg_mushroom.setVisibility(View.GONE);
                break;
            case 7:
                veg_onion.setVisibility(View.GONE);
                break;
            case 8:
                veg_pineapple.setVisibility(View.GONE);
                break;
            case 9:
                veg_red_capsicum.setVisibility(View.GONE);
                break;
            case 10:
                veg_red_chilly.setVisibility(View.GONE);
                break;
            case 11:
                veg_tomato.setVisibility(View.GONE);
                break;
            default:
                BackImage.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void NonVegLoadIamge(int finalI){
        switch (finalI){
            case 0:
                non_veg_bacon.setVisibility(View.VISIBLE);
                break;
            case 1:
                non_veg_meat_piece.setVisibility(View.VISIBLE);
                break;
            case 2:
                non_veg_salami.setVisibility(View.VISIBLE);
                break;
            case 3:
                non_veg_saussages.setVisibility(View.VISIBLE);
                break;
            case 4:
                non_veg_slices_of_meat.setVisibility(View.VISIBLE);
                break;
            default:
                BackImage.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void NonVegHideIamge(int finalI){
        switch (finalI){
            case 0:
                non_veg_bacon.setVisibility(View.GONE);
                break;
            case 1:
                non_veg_meat_piece.setVisibility(View.GONE);
                break;
            case 2:
                non_veg_salami.setVisibility(View.GONE);
                break;
            case 3:
                non_veg_saussages.setVisibility(View.GONE);
                break;
            case 4:
                non_veg_slices_of_meat.setVisibility(View.GONE);
                break;
            default:
                BackImage.setVisibility(View.VISIBLE);
                break;
        }
    }


    public  void vegTrueValue(){
        VegtrueValue = VegtrueValue + 1;
    }

    public  void vegFalseValue(){
        if (VegtrueValue == 0){
            VegtrueValue = 0;
        }else{
            VegtrueValue = VegtrueValue - 1;
        }
    }

    public  void NonvegTrueValue(){
        NonVegtrueValue = NonVegtrueValue + 1;
    }

    public  void NonvegFalseValue(){
        if(NonVegtrueValue == 0){
            NonVegtrueValue = 0;
        }else{
            NonVegtrueValue = NonVegtrueValue - 1;
        }
    }
    public void totalPriceTopping(){
        int CrustPrice = 50;
        int vegTopping = VegtrueValue;
        int NonVegTopping = NonVegtrueValue;
        int ans;
        int vans = vegTopping * VegToppingPrice;
        int nvans = NonVegTopping * NonVegToppingPrice;
        ans = CrustPrice + vans + nvans;
        price.setText(String.valueOf(ans));
    }

    public void listInit(){
        vegToppingList.add(0,"black olive");
        vegToppingList.add(1,"corn");
        vegToppingList.add(2,"green capsicum");
        vegToppingList.add(3,"green chilly");
        vegToppingList.add(4,"green olive");
        vegToppingList.add(5,"herb");
        vegToppingList.add(6,"mushroom");
        vegToppingList.add(7,"onion");
        vegToppingList.add(8,"pineapple");
        vegToppingList.add(9,"red capsicum");
        vegToppingList.add(10,"red chilly");
        vegToppingList.add(11,"tomato");

        NonVegToppingList.add(0,"bacon");
        NonVegToppingList.add(1,"meat piece");
        NonVegToppingList.add(2,"salami");
        NonVegToppingList.add(3,"sausages");
        NonVegToppingList.add(4,"meat slice");
    }
    public void insertCart(final String user_id, final String title, final String qunatity, final String price, final String imageUrl, final String pizza_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            Toast.makeText(CustomPizzaActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CustomPizzaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
