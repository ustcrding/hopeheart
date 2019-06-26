package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;

/**
 * 频道按钮view
 *
 * @author dwl
 * @date 2018/5/18.
 */
public class ChannelButtonView extends LinearLayout {

    /**
     * 频道的icon
     */
    private ImageView ivChannelIcon;

    /**
     * 频道的名称
     */
    private TextView tvChannelName;

    public ChannelButtonView(Context context) {
        super(context);

        initView();
    }

    private void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_channel_button, this, true);

        ivChannelIcon = (ImageView) view.findViewById(R.id.iv_channel_icon);

        tvChannelName = (TextView) view.findViewById(R.id.tv_channel_name);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    private void toggle() {

    }
}
