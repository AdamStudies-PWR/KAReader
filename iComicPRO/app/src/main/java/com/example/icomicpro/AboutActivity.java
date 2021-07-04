package com.example.icomicpro;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        TextView appVersion = (TextView) findViewById(R.id.versionLabel);

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
}