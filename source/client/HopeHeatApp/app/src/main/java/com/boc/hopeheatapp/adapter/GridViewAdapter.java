package com.boc.hopeheatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.boc.hopeheatapp.R;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int lastPosition;//定义一个标记为最后选择的位置
    private String[] str = null;

    public void setData(String[] str, int lastPos) {
        this.str = str;
        this.lastPosition = lastPos;
    }

    public void setSeclection(int position) {
        lastPosition = position;
    }

    public GridViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View mView, ViewGroup arg2) {
        GridViewAdapter.ViewHolder holder = null;
        if (mView == null) {
            holder = new GridViewAdapter.ViewHolder();
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_gridview, null);
            holder.check = (TextView) mView.findViewById(R.id.idGridviewCheck);
            mView.setTag(holder);
        } else {
            holder = (GridViewAdapter.ViewHolder) mView.getTag();
        }
        holder.check.setText(str[position] + "");
        if (lastPosition == position) {//最后选择的位置
            holder.check.setBackgroundResource(R.drawable.ic_rectangle2);
        } else {
            holder.check.setBackgroundResource(R.drawable.ic_rectangle1);
        }
        return mView;
    }

    class ViewHolder {
        private TextView check;
    }
}
