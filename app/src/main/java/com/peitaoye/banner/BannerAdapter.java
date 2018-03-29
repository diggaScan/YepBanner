package com.peitaoye.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by PeitaoYe on 2018/3/29.
 */

public class BannerAdapter extends PagerAdapter {
    private List<View> mDataViews;

    private BannerPage.OnItemClickListener mListener;
    public BannerAdapter(List<View> list){
        this.mDataViews=list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

      int pos= position%mDataViews.size();
        View view=mDataViews.get(pos);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            if(mListener!=null){
                mListener.onClick(v,position);
            }

        }
    });
        try{
            container.addView(view);
        }catch(Exception e){
            container.removeView(view);
            container.addView(view);
        }

        return view;
    }

    public void setOnItemClickListener(BannerPage.OnItemClickListener listener){
        this.mListener=listener;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    public int getViewNum(){
       return this.mDataViews.size();
    }

    @Override
    public int getCount() {
        return 1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        boolean result=view==object;
        return view==object;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
