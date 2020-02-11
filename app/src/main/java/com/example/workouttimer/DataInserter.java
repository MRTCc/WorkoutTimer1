package com.example.workouttimer;

import android.content.Context;

public class DataInserter {

    private DbUtils dbUtils;
    private DbManager dbManager;

    public DataInserter(Context context){
        this.dbManager = new DbManager(context);
    }


}
