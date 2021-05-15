package com.example.bloodmatch.model;

import android.os.Parcelable;

import java.io.Serializable;

public abstract class Model implements Serializable {
    protected String ID;

    public String id(){
        return ID;
    }

    public String id(String id){
        ID = id;
        return null;
    }
}
