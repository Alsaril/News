package com.example.igor.news;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import static com.example.igor.news.utility.Const.ACTION_REQUEST;
import static com.example.igor.news.utility.Const.ACTION_RESULT;


public class NewsIntentService extends IntentService {

    public NewsIntentService() {
        super("News Intent Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REQUEST.equals(action)) {
                handleAction(this);
            }
        }
    }

    private void handleAction(Context context) {
        NewsProcessor.process(context);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_RESULT));
    }
}
