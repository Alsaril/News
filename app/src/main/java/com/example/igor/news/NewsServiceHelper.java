package com.example.igor.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import static com.example.igor.news.NewsIntentService.ACTION_REQUEST;
import static com.example.igor.news.NewsIntentService.ACTION_RESULT;

public class NewsServiceHelper {

    private static NewsServiceHelper instance;
    private static Intent serviceIntent;

    private NewsResultListener callback = null;

    private NewsServiceHelper(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_RESULT);

        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                if (callback != null) {
                    callback.onNewsChange();
                }
            }
        }, filter);
    }

    public static synchronized NewsServiceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NewsServiceHelper(context);
            serviceIntent = new Intent(context, NewsIntentService.class).setAction(ACTION_REQUEST);
        }
        return instance;
    }

    public void setCallback(NewsResultListener callback) {
        this.callback = callback;
    }

    public Intent getIntent() {
        return serviceIntent;
    }

    public void update(Context context) {
        context.startService(serviceIntent);
    }

    public interface NewsResultListener {
        void onNewsChange();
    }

}
