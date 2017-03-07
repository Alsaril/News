package com.example.igor.news;

import android.support.annotation.Nullable;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;

public class NewsRest {

    private static NewsLoader loader = new NewsLoader();

    @Nullable
    public static News process(String topic) {
        try {
            return loader.loadNews(topic);
        } catch (IOException e) {
            return null;
        }
    }
}
