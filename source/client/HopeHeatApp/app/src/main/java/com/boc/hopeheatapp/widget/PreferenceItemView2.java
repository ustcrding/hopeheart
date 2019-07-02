package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class PreferenceItemView2 extends LinearLayout {


    TextView tvName;

    TextView tvValue;


    public PreferenceItemView2(Context context) {
        super(context);

        initUI();
    }

    public PreferenceItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI();
    }

    private void initUI() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_preference_item2, this, true);

        tvName = (TextView) view.findViewById(R.id.prefernce_name);

        tvValue = (TextView) view.findViewById(R.id.prefernce_value);

    }

    public void setNameAndValue(int resNameId, int resValueId) {
        tvName.setText(resNameId);
        tvValue.setText(resValueId);
    }

    public void setNameAndValue(String name, String value) {
        tvName.setText(name);
        tvValue.setText(value);
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setValue(String value) {
        tvValue.setText(value);
    }
}
