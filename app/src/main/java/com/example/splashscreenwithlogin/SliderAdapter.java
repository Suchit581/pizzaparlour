package com.example.splashscreenwithlogin;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> color;
    private List<String> colorName;
    private List<Integer> slide_pics;

    public SliderAdapter(Context context, List<Integer> color, List<String> colorName, List<Integer>slide_pics) {
        this.context = context;
        this.color = color;
        this.colorName = colorName;
        this.slide_pics=slide_pics;
    }

    @Override
    public int getCount() {
        return color.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView=view.findViewById(R.id.imageView1);
        RelativeLayout relativeLayout =  view.findViewById(R.id.relLay);

        textView.setText(colorName.get(position));
        relativeLayout.setBackgroundColor(color.get(position));
        imageView.setImageResource(slide_pics.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}