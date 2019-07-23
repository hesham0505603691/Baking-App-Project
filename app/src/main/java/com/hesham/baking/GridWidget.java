package com.hesham.baking;



import android.content.Intent;
import android.widget.RemoteViewsService;


public class GridWidget extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridViewsRemote(this.getApplicationContext());
    }
}


