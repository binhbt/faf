package com.vega.firebase.deeplink;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by leobui on 11/1/2017.
 */

public class FAFirebaseDynamicLinks {
    public interface OnSuccessListener{

    }
    private Intent intent;
    private static volatile FAFirebaseDynamicLinks singleton;
    public static FAFirebaseDynamicLinks getInstance() {
        if (singleton == null) {
            synchronized (FAFirebaseDynamicLinks.class) {
                if (singleton == null) {
                    singleton = new FAFirebaseDynamicLinks();
                }
            }
        }
        return singleton;
    }
    public FAFirebaseDynamicLinks getDynamicLink(@NonNull Intent intent){
            this.intent = intent;
        return this;
    }
}
