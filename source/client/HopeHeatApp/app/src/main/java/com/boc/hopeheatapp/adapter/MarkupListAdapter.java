package com.boc.hopeheatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.MessageEntity;
import com.boc.hopeheatapp.model.VictimEntity;

/**
 * @author dwl
 * @date 2019/7/3.
 */
public class MarkupListAdapter extends KBaseAdapter<VictimEntity> {


    public MarkupListAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.markup_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final VictimEntity entity = itemList.get(position);
        holder.tvName.setText(entity.getVictimName());
        if ("F".equals(entity.getVictimGender())) {
            holder.tvSex.setText(R.string.female);
        } else {
            holder.tvSex.setText(R.string.male);
        };
        holder.tvAge.setText(entity.getVictimAge() + "岁");

        holder.cbSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entity.setChecked(isChecked);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        CheckBox cbSel;
        TextView tvName;
        TextView tvSex;
        TextView tvAge;

        public ViewHolder(View view) {
            cbSel = (CheckBox) view.findViewById(R.id.cb_selected);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvSex = (TextView) view.findViewById(R.id.tv_sex);
            tvAge = (TextView) view.findViewById(R.id.tv_age);
        }
    }
}
