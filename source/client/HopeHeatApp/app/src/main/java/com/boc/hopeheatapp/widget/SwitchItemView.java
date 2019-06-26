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
 * @date 2016/8/20.
 */
public class SwitchItemView extends LinearLayout {

    private ImageView switchLogo;

    private TextView textView;

    private ImageView switchIcon;

    private boolean switchStatus = true;

    private OnToggleChanged listener;

    public SwitchItemView(Context context) {
        super(context);

        initUI();
    }

    public SwitchItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI();
    }

    private void initUI() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_switch_item_view, this, true);

        switchLogo = (ImageView) view.findViewById(R.id.switch_logo);

        textView = (TextView) view.findViewById(R.id.switch_name);

        switchIcon = (ImageView) view.findViewById(R.id.switch_icon);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public void setTitle(String title) {
        textView.setText(title);
    }

    public void setSwitchLogo(int resId) {
        switchLogo.setImageResource(resId);
    }

    public void setSwitch(boolean on) {
        switchStatus = on;

        updateSwitch();
    }

    private void toggle() {
        switchStatus = !switchStatus;
        updateSwitch();

        if (listener != null) {
            listener.onToggle(switchStatus);
        }
    }

    private void updateSwitch() {
        if (switchStatus) {
            switchIcon.setImageResource(R.drawable.icon_switch_on);
        } else {
            switchIcon.setImageResource(R.drawable.icon_switch_off);
        }
    }

    public boolean getSwitchStatus() {
        return switchStatus;
    }


    public interface OnToggleChanged {
        /**
         * @param on
         */
        void onToggle(boolean on);
    }

    public void setOnToggleChanged(OnToggleChanged onToggleChanged) {
        listener = onToggleChanged;
    }
}
