package com.example.igor.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.igor.news.NewsServiceHelper;
import com.example.igor.news.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

public class MainActivity extends AppCompatActivity implements NewsServiceHelper.NewsResultListener {

    static {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build()
        );
    }

    private TextView header;
    private TextView content;
    private TextView date;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Storage storage = Storage.getInstance(this);

        if (storage.loadCurrentTopic().isEmpty()) {
            storage.saveCurrentTopic(Topics.ALL_TOPICS[0]);
        }

        header = (TextView) findViewById(R.id.header);
        content = (TextView) findViewById(R.id.content);
        date = (TextView) findViewById(R.id.date);
        update = (Button) findViewById(R.id.update);
        Button settings = (Button) findViewById(R.id.settings);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        NewsServiceHelper.getInstance(this).setCallback(this);
        onNewsChange();
    }

    private void update() {
        update.setEnabled(false);
        NewsServiceHelper.getInstance(MainActivity.this).update(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NewsServiceHelper.getInstance(this).setCallback(null);
    }

    @Override
    public void onNewsChange() {
        News news = Storage.getInstance(this).getLastSavedNews();
        if (news != null) {
            header.setText(news.getTitle());
            content.setText(news.getBody());
            date.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date(news.getDate())));
        } else {
            header.setText(getString(R.string.load_error));
            content.setText("");
            date.setText("");
        }

        update.setEnabled(true);
    }
}
