package com.example.igor.news.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.igor.news.NewsServiceHelper;
import com.example.igor.news.R;

import ru.mail.weather.lib.Scheduler;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        final Storage storage = Storage.getInstance(this);

        String selected = storage.loadCurrentTopic();
        boolean update = storage.loadIsUpdateInBg();

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.topics);
        for (int i = 0; i < Topics.ALL_TOPICS.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(Topics.ALL_TOPICS[i]);
            rb.setId(i);
            if (Topics.ALL_TOPICS[i].equals(selected)) {
                rb.setChecked(true);
            }
            radioGroup.addView(rb);
        }

        final CheckBox background = (CheckBox) findViewById(R.id.background);
        background.setChecked(update);

        Button save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SettingsActivity.this, R.string.select_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                storage.saveCurrentTopic(Topics.ALL_TOPICS[radioGroup.getCheckedRadioButtonId()]);
                storage.saveIsUpdateInBg(background.isChecked());
                configureBackground(background.isChecked());
                onBackPressed();
            }
        });
    }

    private void configureBackground(boolean checked) {
        NewsServiceHelper helper = NewsServiceHelper.getInstance(this);
        if (checked) {
            Scheduler.getInstance().schedule(this, helper.getIntent(), 60000);
        } else {
            Scheduler.getInstance().unschedule(this, helper.getIntent());
        }
    }
}
