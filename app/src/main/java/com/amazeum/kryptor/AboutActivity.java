package com.amazeum.kryptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AboutActivity extends AppCompatActivity
{
    private final String PREFERENCE_FILE_KEY = "kryptoSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFERENCE_FILE_KEY, MODE_PRIVATE);

        if(preferences.getInt("lang", 0) == 0) setLocale("pl");
        else setLocale("en");
        if(preferences.getInt("theme", 0) == 0) setTheme(R.style.GreenTheme);
        else setTheme(R.style.PinkTheme);

        setContentView(R.layout.activity_about);

        Converter converter = new Converter();

        TextView kryptoVersion = (TextView) findViewById(R.id.versionInfoLabel);
        TextView appVersion = (TextView) findViewById(R.id.appDataLabel);

        kryptoVersion.setText(converter.getVersion());

        Context context = getApplicationContext();
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;

        try
        {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersion.setText(info.versionName);
        } catch (PackageManager.NameNotFoundException error)
        {
            error.printStackTrace();
            appVersion.setText("ERROR");
        }
    }

    public void returnSettings(View view)
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void setLocale(String language)
    {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}