package com.example.igor.news;

import android.content.Context;
import android.widget.Toast;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;

public class NewsProcessor {

    public static void process(Context context) {
        Storage storage = Storage.getInstance(context);
        String topic = storage.loadCurrentTopic();
        News news = NewsRest.process(topic);
        if (news != null) {
            storage.saveNews(news);
        }
    }
}
