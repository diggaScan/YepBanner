package com.peitaoye.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by PeitaoYe on 2018/3/29.
 */

public class BannerPage extends FrameLayout {
    private Context context;

    private BannerIndicator mIndicator;
    private ViewPager mPager;

    private long idle_occasion;

    private int scrolling_delay = 4 * 1000;

    private int init_pos;
    private PagerAdapter mAdapter;
    private int items_num;

    private int mPagerState = ViewPager.SCROLL_STATE_IDLE;

    private OnItemClickListener mListener;
    private boolean isAutoScroll = true;

    public BannerPage(Context context) {
        this(context, null);
    }

    public BannerPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    //inflate ViewPager and Indicator into Bannger.
    public void init() {
        mIndicator = new BannerIndicator(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        mIndicator.setLayoutParams(lp);

        mPager = new ViewPager(context);
        ViewPager.LayoutParams vp_lp = new ViewPager.LayoutParams();
        vp_lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        vp_lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        mPager.setLayoutParams(vp_lp);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        if (mAdapter != null) {
            this.mListener = listener;
            ((BannerAdapter) mAdapter).setOnItemClickListener(listener);
        }
    }

    public void setAdapter(PagerAdapter adapter) {
        Log.d("info", "tag");
        this.mAdapter = adapter;

        mPager.setAdapter(adapter);
        int num = adapter.getCount();
        init_pos = num / 2 - (num / 2) % 3;
        mPager.setCurrentItem(init_pos);
        mPager.addOnPageChangeListener(mPagerListener);
        items_num = ((BannerAdapter) adapter).getViewNum();
        mIndicator.setItem_nums(items_num);
        addView(mIndicator);
        addView(mPager);
        //set up Auto-Scrolling banner
        if (isAutoScroll) {
            postDelayed(mScrollingTask, scrolling_delay);
        }
    }


    private Runnable mScrollingTask = new Runnable() {
        @Override
        public void run() {
            if (mPagerState == ViewPager.SCROLL_STATE_IDLE) {
                Log.d("info", "current state: IDLE");
            } else if (mPagerState == ViewPager.SCROLL_STATE_DRAGGING) {
                Log.d("info", "current state:dragging");

            }

            long cur_time = System.currentTimeMillis();
            long time_gap = cur_time - idle_occasion;

            if (mPagerState == ViewPager.SCROLL_STATE_IDLE) {
                if (time_gap < scrolling_delay * 0.35) {
                    postDelayed(mScrollingTask, (long) (scrolling_delay * 0.65));
                } else {
                    init_pos++;
                    setToNextPage(init_pos);
                    postDelayed(mScrollingTask, scrolling_delay);
                }
            } else if (mPagerState == ViewPager.SCROLL_STATE_DRAGGING) {
                postDelayed(mScrollingTask, scrolling_delay);
            }


        }
    };

    private void setToNextPage(int position) {
        mPager.setCurrentItem(position);

    }

    public void setAutoScroll(boolean autoScroll) {

        isAutoScroll = autoScroll;
    }

    public int getScrolling_delay() {
        return scrolling_delay;
    }

    public void setScrolling_delay(int scrolling_delay) {
        this.scrolling_delay = scrolling_delay;
    }


    //listen for the change of
    private ViewPager.OnPageChangeListener mPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Log.d("info","position:"+position+"offset:"+positionOffset+"Pixels:"+positionOffsetPixels);
            init_pos = position;
            int pos = position % 3;
            mIndicator.setCurrentPosition(pos, positionOffset);

        }

        @Override
        public void onPageSelected(int position) {
            Log.d("info", "onPagerSelected()");

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("info", "onPagerScrollchanged()");
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                Log.d("info", "SCROLL_STATE_DRAGGING");
                mPagerState = ViewPager.SCROLL_STATE_DRAGGING;
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                Log.d("info", "SCROLL_STATE_IDLE");
                idle_occasion = System.currentTimeMillis();
                mPagerState = ViewPager.SCROLL_STATE_IDLE;
            }
        }
    };

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


}
