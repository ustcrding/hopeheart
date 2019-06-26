package com.boc.hopeheatapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.senab.photoview.PhotoView;

/**
 * 图片浏览程序
 *
 * @auther ruiding
 * @date 2015/12/6.
 */
public class PictureShowActivity extends Activity {

    private static final int DEFAULT_PAGE_INDEX = 0;
    private ViewPager mViewPager = null;
    private TextView mPagerIndexView = null;
    private ImageAdapter mImageAdapter = null;
    private List<String> mPicList = null;
    private int pageIndex = DEFAULT_PAGE_INDEX;

    private Map<String, Bitmap> mBmpMap = new HashMap<String, Bitmap>(9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉信息栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_picture_show);
        initUI();
    }

    private void initUI() {

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerIndexView = (TextView) findViewById(R.id.pager_index_id);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int postion) {
                if (null != mImageAdapter) {
                    String indexStr = String.format(getString(R.string.pager_index_format_str), postion + 1, mImageAdapter.getCount());
                    mPagerIndexView.setText(indexStr);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        mPicList = getIntent().getStringArrayListExtra(ActivityJumper.INTENT_DATA);
        pageIndex = getIntent().getIntExtra(ActivityJumper.INTENT_PAGE_INDEX, DEFAULT_PAGE_INDEX);

        if (null != mPicList && !mPicList.isEmpty()) {
            mImageAdapter = new ImageAdapter(mPicList);
            mViewPager.setAdapter(mImageAdapter);
            String indexStr = String.format(getString(R.string.pager_index_format_str), 1, mImageAdapter.getCount());
            mPagerIndexView.setText(indexStr);

            pageIndex = pageIndex >= mPicList.size() ? mPicList.size() - 1 : pageIndex;
            mViewPager.setCurrentItem(pageIndex, false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null != mBmpMap) {
            for (String ele : mBmpMap.keySet()) {
                if (!TextUtils.isEmpty(ele)) {
                    Bitmap bmp = mBmpMap.get(ele);
                    if (null != bmp) {
                        if (!bmp.isRecycled()) {
                            bmp.recycle();
                            bmp = null;
                        }
                    }
                }
            }
        }
    }

    private class ImageAdapter extends PagerAdapter {

        private LayoutInflater inflater;
        private List<String> list;

        public ImageAdapter(List<String> list) {
            inflater = LayoutInflater.from(PictureShowActivity.this);
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {

            View imageLayout = inflater.inflate(R.layout.adapter_picture_show_item, view, false);
            assert imageLayout != null;

            final PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.img_view_id);
            final ProgressBar pogressBar = (ProgressBar) imageLayout.findViewById(R.id.progress_bar);

            String url = list.get(position);
            Glide.with(PictureShowActivity.this)
                    .load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    pogressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    imageView.setImageDrawable(resource);
                    pogressBar.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(R.drawable.icon_empty).error(R.drawable.icon_error).into(imageView);
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }
}
