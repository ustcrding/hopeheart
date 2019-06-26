package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.phone.DensityUtils;

import java.util.List;

/**
 * 语音引导view
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class VoiceGuideView extends LinearLayout {

    private static final String TAG = "VoiceGuideView";

    private ImageView ivHeadIcon;
    private TextView tvIntroduce;
    private TextView tvSpeakTitle;
    private LinearLayout guidContainer;
    private GuideItemClickedListener guideItemClickedListener;

    public VoiceGuideView(Context context) {
        super(context);

        initView();
    }

    public VoiceGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public VoiceGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_voice_guide, this, true);

        ivHeadIcon = (ImageView) view.findViewById(R.id.iv_left);
        tvIntroduce = (TextView) view.findViewById(R.id.tv_introduce);
        tvSpeakTitle = (TextView) view.findViewById(R.id.tv_speak_title);
        guidContainer = (LinearLayout) view.findViewById(R.id.ll_guide_container);

        if (UserManager.getInstance() != null && UserManager.getInstance().getUser() != null) {
            UserEntity userEntity = UserManager.getInstance().getUser();
            String username = userEntity.getUsername();
            String welcome = String.format(getContext().getString(R.string.voice_assist_header_msg_format), username);
            tvIntroduce.setText(welcome);
        } else {
            tvIntroduce.setText(R.string.voice_assist_header_msg2);
        }

    }

    public void setData(List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        guidContainer.removeAllViews();
        for (String content : list) {
            TextView textView = genTextView(content);

            guidContainer.addView(textView);
        }
    }

    private TextView genTextView(final String content) {
        TextView textView = new TextView(getContext());
        textView.setText(content);
        textView.setTextColor(getContext().getResources().getColorStateList(R.color.color_voice_guide_item));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        int left = DensityUtils.dip2Px(getContext(), 30);
        int top = DensityUtils.dip2Px(getContext(), 5);
        int bottom = DensityUtils.dip2Px(getContext(), 5);
        textView.setPadding(left, top, 0, bottom);
        textView.setGravity(Gravity.LEFT);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setSingleLine();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "genTextView.onClick | content = " + content);
                }

                if (guideItemClickedListener != null) {
                    guideItemClickedListener.onGuideItemClicked(v, content);
                }
            }
        });
        return textView;
    }

    public void setGuideItemClickedListener(GuideItemClickedListener guideItemClickedListener) {
        this.guideItemClickedListener = guideItemClickedListener;
    }

    /**
     * 当某一条被点击时回调
     */
    public static interface GuideItemClickedListener {
        void onGuideItemClicked(View v, String content);
    }

    /**
     * 播报介绍文字
     */
    public void speak() {
        String text = tvIntroduce.getText().toString();
        TtsManager.getInstance(getContext()).speak(text, null);
    }
}
