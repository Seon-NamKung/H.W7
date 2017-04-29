package com.example.mskir.hw7;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mskir.hw6.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by mskir on 2017-04-28.
 */

public class DataAdapter extends BaseAdapter implements Filterable{
    private ArrayList<Data> dataList;
    private ArrayList<Data> tempList;
    private Context context;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    private Boolean isVisible = false;

    Filter listFilter;

    public DataAdapter(Context context, ArrayList<Data> dataList){
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
        tempList = dataList;
    }

    @Override
    public int getCount() {
        return tempList.size();
    }

    @Override
    public Data getItem(int position) {
        return tempList.get(position);
    }

    public ArrayList<Data> getList(){
        return dataList;
    }
    public void setList(){tempList = dataList;}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu, null);
        }
        viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
        viewHolder.tv_name = (TextView) v.findViewById(R.id.tv_name);
        viewHolder.tv_pnum = (TextView) v.findViewById(R.id.tv_pnum);
        viewHolder.cb_check = (CheckBox) v.findViewById(R.id.cb_check);

        v.setTag(viewHolder);
        viewHolder.iv_image.setImageResource(tempList.get(position).getCategory());
        viewHolder.tv_name.setText(tempList.get(position).getName());
        viewHolder.tv_pnum.setText(tempList.get(position).getCall());

        if (isVisible) {
            viewHolder.cb_check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.cb_check.setVisibility(View.INVISIBLE);
        }
        final int x = position;
        viewHolder.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataList.get(x).setIsChecked(isChecked);
                Log.d("Debug",Boolean.toString(dataList.get(x).getIsChecked()));
            }
        });

        return v;
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }


    class ViewHolder{
        public ImageView iv_image = null;
        public TextView tv_name = null;
        public TextView tv_pnum = null;
        public CheckBox cb_check = null;
    }

    public void viewCheckBox(Boolean isView){
            isVisible = isView;
    }

    public class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                results.values = dataList;
                results.count = dataList.size();
            }else{
                ArrayList<Data> itemList = new ArrayList<Data>();

                for(Data item : dataList){
                    if(item.getName().toUpperCase().contains(constraint.toString().toUpperCase())){
                        itemList.add(item);
                    }
                }
                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            tempList = (ArrayList<Data>) results.values;

            if(results.count > 0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }

        }
    }


}
