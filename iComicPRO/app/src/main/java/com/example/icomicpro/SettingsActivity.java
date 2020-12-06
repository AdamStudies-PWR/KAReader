package com.example.icomicpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity
{
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    Switch screenSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        edit = preferences.edit();

        boolean screenMode = preferences.getBoolean("fullscreen", true);

        screenSwitch = (Switch) findViewById(R.id.screen_switch);
        screenSwitch.setChecked(screenMode);
    }

    public void openAbout(View view)
    {
        Intent launchIntent = new Intent(this, AboutActivity.class);
        startActivityForResult(launchIntent, 2);
    }

    public void switchScreen(View view)
    {
        edit.putBoolean("fullscreen", screenSwitch.isChecked());
        edit.apply();
    }

    public void goBack(View view)
    {
        onBackPressed();
    }
}