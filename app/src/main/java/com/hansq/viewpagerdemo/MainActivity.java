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


public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {

    /**
     * ViewPager
     */
    private ViewPager viewPager;

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    /**
     * 图片资源id
     */
    private int[] imgIdArray;

    /**
     * 图片总数
     */
    private int imageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        imgIdArray = new int[]{
                R.mipmap.item_1,
                R.mipmap.item_2,
                R.mipmap.item_3,
                R.mipmap.item_4,
                R.mipmap.item_5,
                R.mipmap.item_6,
        };
        imageCount = imgIdArray.length;
        //将点加入到ViewGroup中
        tips = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = imageView;

            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.white_dot);
            } else {
                tips[i].setBackgroundResource(R.mipmap.gray_dot);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
            ));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);

        }
        //将图片装载到数组中
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);

        }
        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((mImageViews.length) * 100);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % imageCount);
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
        for (int i = 0; i < imageCount; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.white_dot);
            } else {
                tips[i].setBackgroundResource(R.mipmap.gray_dot);
            }
        }
    }

    //内部Adapter类
    class MyAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews[position % imageCount], 0);
            return mImageViews[position % imageCount];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews[position % imageCount]);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}

