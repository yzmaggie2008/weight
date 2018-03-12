package com.example.yumimama.weightloss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yumimama on 2/5/18.
 */

public class HistoryAdapter extends BaseAdapter {
    private List<historyData> histories;
    private Context context;
    public HistoryAdapter(Context context,List<historyData> histories) {
        super();
        this.histories=histories;
        this.context=context;
    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public Object getItem(int i) {
        return histories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_history,viewGroup,false);
        }

        TextView tvDate= (TextView) view.findViewById(R.id.date);
        TextView tvWeight= (TextView) view.findViewById(R.id.weight);


        tvDate.setText("Date  "+histories.get(i).getDate());
        tvWeight.setText("Weight  "+histories.get(i).getWeight());
        return view;
    }
}
