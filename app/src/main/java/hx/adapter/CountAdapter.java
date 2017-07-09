package hx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hx.bean.CountBean;
import hx.counttimer.R;

/**
 * Created by mirui on 2017/7/5.
 */

public class CountAdapter extends BaseAdapter {
    private List<CountBean> secondData = new ArrayList<>();
    private Context mContext;
    private CountHolder countHolder;

    public CountAdapter(List<CountBean> lastSecond, Context context) {
        secondData = lastSecond;
        mContext = context;
    }

    @Override
    public int getCount() {
        return secondData.size();
    }

    @Override
    public Object getItem(int i) {
        return secondData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_timer, null);
            countHolder = new CountHolder();
            countHolder.tv_timer = (TextView) view.findViewById(R.id.tv_timer);
        }else{
            countHolder = (CountHolder) view.getTag();
        }
        countHolder.tv_timer.setText(secondData.get(i).getCountTime()+"");
        return view;
    }


    static class CountHolder{
        TextView tv_timer;
    }
}
