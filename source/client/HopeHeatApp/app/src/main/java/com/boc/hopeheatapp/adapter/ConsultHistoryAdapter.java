package com.boc.hopeheatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ConsultHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zpwu3 on 2019-07-02.
 */
public class ConsultHistoryAdapter extends BaseAdapter {
    List<ConsultHistoryEntity.Result> mDatas;
    private Context mContext;

    public ConsultHistoryAdapter(Context context) {
        mDatas = new ArrayList<ConsultHistoryEntity.Result>();
        mContext = context;
    }

    public void setData(List<ConsultHistoryEntity.Result> datas) {
        mDatas.clear();
        mDatas.addAll(datas);

    }

    public List<ConsultHistoryEntity.Result> getDatas() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConsultHistoryAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ConsultHistoryAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.consult_history_item, null);
            holder.tvStartDate = (TextView) convertView.findViewById(R.id.tv_start_date);
            holder.tvEndDate = (TextView) convertView.findViewById(R.id.tv_end_date);
            holder.tvProvince = (TextView) convertView.findViewById(R.id.tv_province);
            holder.tvCity = (TextView) convertView.findViewById(R.id.tv_city);
            convertView.setTag(holder);
        } else {
            holder = (ConsultHistoryAdapter.ViewHolder) convertView.getTag();
        }

        ConsultHistoryEntity.Result result = mDatas.get(position);
        holder.tvStartDate.setText(result.getTestioinDate());
        //holder.tvEndDate.setText(result.getTestioinDate());
        if ("H".equals(result.getTestsLevel())) {
            holder.tvEndDate.setText(R.string.evaluation_result1);
        } else if ("U".equals(result.getTestsLevel())) {
            holder.tvEndDate.setText(R.string.evaluation_result2);
        } else if ("B".equals(result.getTestsLevel())) {
            holder.tvEndDate.setText(R.string.evaluation_result3);
        } else if ("I".equals(result.getTestsLevel())) {
            holder.tvEndDate.setText(R.string.evaluation_result4);
        }

        holder.tvCity.setText(result.getAddressCode());

        return convertView;
    }

    class ViewHolder {
        TextView tvStartDate;
        TextView tvEndDate;
        TextView tvProvince;
        TextView tvCity;
    }
}
