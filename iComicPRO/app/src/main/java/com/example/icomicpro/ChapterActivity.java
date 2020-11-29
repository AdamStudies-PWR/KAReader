package com.example.icomicpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChapterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.e("INFO", "WAS CALLED");

        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }
}