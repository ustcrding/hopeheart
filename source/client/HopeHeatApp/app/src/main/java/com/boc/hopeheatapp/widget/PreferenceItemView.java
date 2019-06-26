package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;

/**
 * @auther ruiding
 * @date 2015/11/15.
 */
public class PreferenceItemView extends LinearLayout {

    ImageView ivIcon;

    TextView tvName;

    ImageView btnMore;

    ImageView reddot;


    public PreferenceItemView(Context context) {
        super(context);

        initUI();
    }

    public PreferenceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI();
    }

    private void initUI() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_preference_item, this, true);

        ivIcon = (ImageView) view.findViewById(R.id.prefernce_picture);

        tvName = (TextView) view.findViewById(R.id.prefernce_name);

        btnMore = (ImageView) view.findViewById(R.id.prefernce_more);

        reddot = (ImageView) view.findViewById(R.id.prefernce_reddot);
    }

    public void setIconAndName(int iconId, int resId) {
        ivIcon.setImageResource(iconId);
        tvName.setText(resId);

        if (iconId == -1) {
            ivIcon.setVisibility(View.GONE);
            //float commonMargin =  16.5f;
            //int left = UIUtil.dip2px(getContext(), commonMargin);
            int left = getResources().getDimensionPixelSize(R.dimen.common_margin);
            tvName.setPadding(left, 0, 0, 0);
        }
    }

    public void setIconAndName(int iconId, String name) {
        if (iconId != -1) {
            ivIcon.setImageResource(iconId);
        }
        tvName.setText(name);

        if (iconId == -1) {
            ivIcon.setVisibility(View.GONE);
            //float commonMargin =  16.5f;
            //int left = UIUtil.dip2px(getContext(), commonMargin);
            int left = getResources().getDimensionPixelSize(R.dimen.common_margin);
            tvName.setPadding(left, 0, 0, 0);
        }
    }

    /**
     * 设置是否显示小红点
     *
     * @param show
     */
    public void showReddot(boolean show) {
        if (show) {
            reddot.setVisibility(View.VISIBLE);
        } else {
            reddot.setVisibility(View.GONE);
        }

    }

}
