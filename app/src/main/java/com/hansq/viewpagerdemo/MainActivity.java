package com.hansq.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    /**
     * ViewPager
     */
    private ViewPager mViewPager;

    /**
     * 装点的ImageView数组
     */
    private ImageView[] mTips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    /**
     * 图片资源id
     */
    private int[] mImageIdArray;

    /**
     * 图片总数
     */
    private int mImageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mImageIdArray = new int[]{
                R.mipmap.item_1,
                R.mipmap.item_2,
                R.mipmap.item_3,
        };
        mImageCount = mImageIdArray.length;
        //将图片下方的同步点加入到ViewGroup中
        mTips = new ImageView[mImageCount];
        if(mImageCount>1){
        for (int i = 0; i < mImageCount; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            mTips[i] = imageView;

            if (i == 0) {
                mTips[i].setBackgroundResource(R.mipmap.white_dot);
            } else {
                mTips[i].setBackgroundResource(R.mipmap.gray_dot);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
            ));
            layoutParams.setMargins(5,0,5,0);
            group.addView(imageView, layoutParams);
        }
        }
        //将图片装载到数组中
        mImageViews = new ImageView[mImageCount];
        for (int i = 0; i < mImageCount; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(mImageIdArray[i]);
        }
        //设置Adapter
        mViewPager.setAdapter(new TravelTipsAdapter());
        //设置监听，主要是设置点点的背景，addOnPageChangeListener()取代了setOnPageChangeListener()
        mViewPager.addOnPageChangeListener(new TravelTipsViewPagerPageChangeListener());
        //条件1：设置ViewPager的默认项, 设置为默认已经向左滑动了100次，只是自定义了100
        // 这样开始就能往右滑动，否则初始不能向右互动
//        mViewPager.setCurrentItem((mImageViews.length) * 100);

    }

    /**
     * 旅行提示的Adapter类
     * //若想循环滑动，则应同时打开条件1与条件2
     */
    class TravelTipsAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageCount;
//            条件2
//            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews[position % mImageCount], 0);
            return mImageViews[position % mImageCount];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews[position % mImageCount]);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 旅行提示的OnPageChangeListener
     */
    class TravelTipsViewPagerPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //只有1张图片时，不显示图片下面的点
            if(mImageCount>1) {
                setImageBackground(position % mImageCount);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        /**
         * 设置选中的tip的背景
         *
         * @param selectItems
         */
        private void setImageBackground(int selectItems) {
            for (int i = 0; i < mImageCount; i++) {
                if (i == selectItems) {
                    mTips[i].setBackgroundResource(R.mipmap.white_dot);
                } else {
                    mTips[i].setBackgroundResource(R.mipmap.gray_dot);
                }
            }
        }
    }

}

