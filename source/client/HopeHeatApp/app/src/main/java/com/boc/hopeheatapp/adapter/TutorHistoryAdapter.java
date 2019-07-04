package com.boc.hopeheatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.TutorHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public class TutorHistoryAdapter extends BaseAdapter {
    List<TutorHistoryEntity.Result> mDatas;
    private Context mContext;

    public TutorHistoryAdapter(Context context) {
        mDatas = new ArrayList<TutorHistoryEntity.Result>();
        mContext = context;
    }

    public void setData(List<TutorHistoryEntity.Result> datas) {
        mDatas.clear();
        mDatas.addAll(datas);

    }

    public List<TutorHistoryEntity.Result> getDatas() {
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.consult_history_item, null);
            holder.tvEndDateLabel  = (TextView) convertView.findViewById(R.id.tv_end_date_label);
            holder.tvStartDate = (TextView) convertView.findViewById(R.id.tv_start_date);
            holder.tvEndDate = (TextView) convertView.findViewById(R.id.tv_end_date);
            holder.tvProvince = (TextView) convertView.findViewById(R.id.tv_province);
            holder.tvCity = (TextView) convertView.findViewById(R.id.tv_city);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TutorHistoryEntity.Result result = mDatas.get(position);
        holder.tvStartDate.setText(result.getTestioinDate());

        holder.tvEndDateLabel.setVisibility(View.GONE);
        holder.tvEndDate.setVisibility(View.GONE);

        holder.tvCity.setText(result.getAddressCode());

        return convertView;
    }

    class ViewHolder {
        TextView tvStartDate;
        TextView tvEndDate;
        TextView tvProvince;
        TextView tvCity;

        TextView tvEndDateLabel;
    }
}
