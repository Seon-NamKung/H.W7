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
    private ArrayList<Integer> checkedList = new ArrayList<Integer>();

    Filter listFilter;

    public DataAdapter(Context context, ArrayList<Data> dataList){
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
        tempList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Data getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int x = position;
        View v = convertView;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu, null);
            viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
            viewHolder.tv_name = (TextView) v.findViewById(R.id.tv_name);
            viewHolder.tv_pnum = (TextView) v.findViewById(R.id.tv_pnum);
            viewHolder.cb_check = (CheckBox) v.findViewById(R.id.cb_check);

            viewHolder.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Integer integer = x;
                    if(isChecked){
                        checkedList.add(integer);
                    }else{
                        checkedList.remove(integer);
                    }
                }
            });
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.iv_image.setImageResource(dataList.get(position).getCategory());
        viewHolder.tv_name.setText(dataList.get(position).getName());
        viewHolder.tv_pnum.setText(dataList.get(position).getCall());

        if (isVisible) {
            viewHolder.cb_check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.cb_check.setVisibility(View.INVISIBLE);
            viewHolder.cb_check.setChecked(false );
        }


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

    //체크된 아이템 삭제
    public ArrayList<Data> deleteCheckedItem(){
        Comparator<Integer> nameAsc = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        Collections.sort(checkedList, nameAsc);
        for(int i = checkedList.size()-1;i>=0;i--){
            int x = checkedList.get(i);
            dataList.remove(x);
        }
        checkedList.clear();
        return dataList;
    }

    public void reset(){
        dataList = tempList;
    }

    public class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            reset();
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

            dataList = (ArrayList<Data>) results.values;

            if(results.count > 0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }

        }
    }
}
