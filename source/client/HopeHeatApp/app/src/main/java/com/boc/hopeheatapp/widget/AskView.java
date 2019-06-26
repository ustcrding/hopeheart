package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.MsgEntity;

/**
 * 问答页面view
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class AskView extends RelativeLayout {

    private ImageView ivHeadIcon;
    private TextView tvAskContent;

    public AskView(Context context) {
        super(context);

        initView();
    }

    private void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_ask, this, true);

        ivHeadIcon = (ImageView) view.findViewById(R.id.iv_head_icon);
        tvAskContent = (TextView) view.findViewById(R.id.tv_ask_content);
    }

    public void setMsg(MsgEntity msg) {
        tvAskContent.setText(msg.getContent().toString());
    }

}
