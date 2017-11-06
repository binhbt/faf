package com.vega.firebase.ga;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leobui on 11/1/2017.
 */

public class FAFireBaseAnalytic {
    public static final String ROOT_CATEGORY ="GA_APPLICATION";
    public static final String DEFAULT_CATEGORY ="CATEGORY";
    public static final String DEFAULT_ACTION ="ACTION";
    public static final String DEFAULT_LABEL="LABEL";

    private String rootCategory = ROOT_CATEGORY;
    private String category = DEFAULT_CATEGORY;
    private String action = DEFAULT_ACTION;
    private String label = DEFAULT_LABEL;

    private List<String> categoryParams = new ArrayList<>();

    private FirebaseAnalytics mFirebaseAnalytics;
    private static volatile FAFireBaseAnalytic singleton;
    public FAFireBaseAnalytic(Context ctx){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(ctx);
        addParams();
    }
    private void addParams(){
        categoryParams.add(rootCategory);
        categoryParams.add(category);
        categoryParams.add(action);
        categoryParams.add(label);
    }
    public static FAFireBaseAnalytic getDefault() {
        if (singleton == null) {
            return null;
        }else{
            return singleton;
        }

    }
    public static FAFireBaseAnalytic init(Context ctx) {
        if (singleton == null) {
            synchronized (FAFireBaseAnalytic.class) {
                if (singleton == null) {
                    singleton = new FAFireBaseAnalytic(ctx);
                }
            }
        }
        return singleton;
    }
    public static FAFireBaseAnalytic getDefault(Context ctx) {
        if (singleton == null) {
            synchronized (FAFireBaseAnalytic.class) {
                if (singleton == null) {
                    singleton = new FAFireBaseAnalytic(ctx);
                }
            }
        }
        return singleton;
    }
    public FAFireBaseAnalytic rootCategory(String rootCategory){
        this.rootCategory = rootCategory;
        categoryParams.clear();
        addParams();
        return this;
    }
    public FAFireBaseAnalytic category(String category){
        this.category = category;
        categoryParams.clear();
        addParams();
        return this;
    }
    public FAFireBaseAnalytic action(String action){
        this.action = action;
        categoryParams.clear();
        addParams();
        return this;
    }
    public FAFireBaseAnalytic categories(List<String> categories){
        if (categories != null && categories.size() >0){
            categoryParams = categories;
        }
        return this;
    }
    public void sendEvent(String category, String action, String label, int index) {
        if (mFirebaseAnalytics != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString(categoryParams.get(1), category.toUpperCase());
                bundle.putString(categoryParams.get(2), action.toUpperCase());
                bundle.putString(categoryParams.get(3), index + "_" + label.toUpperCase());
                // Firebase Analytics warning: Name must consist of letters, digits or _ (underscores). Type, name: event, Play By Content
                mFirebaseAnalytics.logEvent(categoryParams.get(0), bundle);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void sendEvent(String... params){
        if (categoryParams != null && categoryParams.size() >1){
            Bundle bundle = new Bundle();
            for (int i=1; i<categoryParams.size(); i++){
                try {
                    bundle.putString(categoryParams.get(i), params[i -1].toUpperCase());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                mFirebaseAnalytics.logEvent(categoryParams.get(0), bundle);
            }
        }
    }
}
