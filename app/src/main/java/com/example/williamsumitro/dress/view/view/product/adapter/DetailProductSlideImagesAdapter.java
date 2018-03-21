package com.example.williamsumitro.dress.view.view.product.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.williamsumitro.dress.R;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 06/03/2018.
 */

public class DetailProductSlideImagesAdapter extends PagerAdapter {
    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    boolean isImageFitToScreen;
    private Context context;
    public  DetailProductSlideImagesAdapter(Context context, ArrayList<Integer> images){
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return images.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.detailproduct_slideimage, view, false);
        final ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.detailproductslideimage_image);
        myImage.setImageResource(images.get(position));
        view.addView(myImageLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
