package com.example.igor.news;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


public class NewsIntentService extends IntentService {

    public static final String ACTION_REQUEST = "action.news_request";
    public static final String ACTION_RESULT = "action.news_result";

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
