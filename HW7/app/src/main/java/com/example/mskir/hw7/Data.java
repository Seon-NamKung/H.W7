package com.example.mskir.hw7;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mskir on 2017-04-06.
 */

public class Data implements Parcelable{
    private String name;
    private String call;
    private String[] menu;
    private String homepage;
    private String registered;
    private int category;

    public Data(String name, String call,String[] menu,
                String homepage,String registered,int category){
        this.name = name;
        this.call = call;
        this.menu = menu;
        this.homepage = homepage;
        this.registered = registered;
        this.category = category;
    }
/*    public void setData(String name, String call,String[] menu,
                String homepage,String registered,int category){
        this.name = name;
        this.call = call;
        this.menu = menu;
        this.homepage = homepage;
        this.registered = registered;
        this.category = category;
    }*/

    protected Data(Parcel in) {
        name = in.readString();
        call = in.readString();
        menu = in.createStringArray();
        homepage = in.readString();
        registered = in.readString();
        category = in.readInt();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    String getName(){
        return name;
    }

    String getCall(){
        return call;
    }

    String[] getMenu(){
        return menu;
    }

    String getHp(){
        return homepage;
    }

    String getRegistered(){
        return registered;
    }

    int getCategory(){
        return category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(call);
        dest.writeStringArray(menu);
        dest.writeString(homepage);
        dest.writeString(registered);
        dest.writeInt(category);
    }
}
