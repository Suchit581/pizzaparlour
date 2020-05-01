package com.example.splashscreenwithlogin;

import android.os.Bundle;
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
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.splashscreenwithlogin.Adapter.MyAdapter;
import com.example.splashscreenwithlogin.ViewHolders.TitleChildViewHolder;
import com.example.splashscreenwithlogin.exp_models.TitleChild;
import com.example.splashscreenwithlogin.exp_models.TitleCreator;
import com.example.splashscreenwithlogin.exp_models.TitleParent;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        loadProducts();
//        return inflater.inflate(R.layout.fragment_order, container, false);
//    }

    RecyclerView recyclerView;
//    List<TitleParent> parentList;
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView = view.findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setRecyclerListener(mRecycleListener);
        TitleCreator titleCreator = TitleCreator.get(getActivity());
        MyAdapter adapter = new MyAdapter(getContext(),titleCreator.getAll());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);
        return view;
    }

//    private List<ParentObject> initData() {
//        TitleCreator titleCreator = TitleCreator.get(getActivity());
//        List<TitleParent> titles = titleCreator.getAll();
//        List<ParentObject> parentObject = new ArrayList<>();
//        for(TitleParent title:titles)
//        {
//            List<Object> childList = new ArrayList<>();
//            childList.add(new TitleChild("Time","latitude","longitude","Location Description"));
//            title.setChildObjectList(childList);
//            parentObject.add(title);
//        }
//        return parentObject;
//
//    }

    private RecyclerView.RecyclerListener mRecycleListener = new RecyclerView.RecyclerListener() {

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            TitleChildViewHolder mapHolder = (TitleChildViewHolder) holder;
            if (mapHolder != null && mapHolder.map != null) {
                // Clear the map and free up resources by changing the map type to none.
                // Also reset the map when it gets reattached to layout, so the previous map would
                // not be displayed.
                mapHolder.map.clear();
                mapHolder.map.setMapType(GoogleMap.MAP_TYPE_NONE);
            }
        }
    };


}
