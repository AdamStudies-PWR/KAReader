package com.example.icomicpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    boolean firstTime = true; //temporary
    TextView opened = null;
    LinearLayout newLL = null;

    final String comicDir = "Komiksy";
    final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    ArrayList<Series> comics = new ArrayList<Series>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(permissions, 1);
        loadContent();

        //       SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);

        //       if(sharedPreferences.getBoolean(getString(R.string.first_time_key), true))
        if (firstTime) firstTime();

            //------------------------------------------------------------------------------------------
            // kinda ugly and temporary but i'll make it dynamic
            //------------------------------------------------------------------------------------------

        else
        {
            appearifySeries();
        }
    }

    void appearifySeries()
    {
        final LinearLayout scrollLayout = findViewById(R.id.linearMain);

        scrollLayout.setPadding(40, 40, 40, 40);

        Button button = new Button(this);
        button.setText("zamknieta seria 1");
        button.setBackground(getDrawable(R.drawable.gradient));
        button.setTextSize(20);
        button.setTextColor(getColor(R.color.colorText));
        button.setHeight(70);
        button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button.setPadding(40, 20, 20, 20);
        scrollLayout.addView(button);

        final Button button2 = new Button(this);
        button2.setText("zamknieta seria 2");
        button2.setBackground(getDrawable(R.drawable.gradient));
        button2.setTextSize(20);
        button2.setTextColor(getColor(R.color.colorText));
        button2.setHeight(70);
        button2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button2.setPadding(40, 20, 20, 20);
        scrollLayout.addView(button2);

        final ScrollView newSV = new ScrollView(this);
        newLL = new LinearLayout(this);
        newLL.setOrientation(LinearLayout.VERTICAL);
        newLL.setBackgroundColor(getColor(R.color.colorAccent));
        newSV.addView(newLL);

        scrollLayout.addView(newSV, scrollLayout.indexOfChild(button2) + 1); //fix this adding a new divider!!

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final TextView letssee = new TextView(v.getContext());
                letssee.setText("> rozdzial " + newLL.getChildCount());
                if ((newLL.getChildCount() % 2 == 0))
                {
                    letssee.setBackgroundColor(getColor(R.color.colorAccent));
                } else
                {
                    letssee.setBackgroundColor(getColor(R.color.colorAccent2));
                }
                letssee.setTextSize(16);
                letssee.setTextColor(getColor(R.color.colorText));
                letssee.setPadding(60, 20, 0, 20);
                newLL.addView(letssee);

                letssee.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        letssee.setBackgroundColor(getColor(R.color.colorText));
                        letssee.setTextColor(getColor(R.color.colorAccent));

                        opened = letssee;
                        goToChapter(newLL.indexOfChild(letssee));
                    }
                });

                newLL.setPadding(0, 0, 0, 20);

                button2.setText("otwarta seria");

            }
        });

        Button button3 = new Button(this);
        button3.setText("zamknieta seria 3");
        button3.setBackground(getDrawable(R.drawable.gradient));
        button3.setTextSize(20);
        button3.setTextColor(getColor(R.color.colorText));
        button3.setHeight(70);
        button3.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button3.setPadding(40, 20, 20, 20);
        scrollLayout.addView(button3);

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (newSV.getVisibility() == View.GONE)
                {
                    newSV.setVisibility(View.VISIBLE);
                    button2.setText("pojawiam sie");
                } else
                {
                    newSV.setVisibility(View.GONE);
                    button2.setText("i znikam ");
                }
            }
        });
    }

    void firstTime()
    {
        // single button to pick a folder - ask for file access permissions on click!

        final LinearLayout scrollLayout = findViewById(R.id.linearMain);

        scrollLayout.setPadding(40, 100, 40, 100);

        Button button = new Button(this);
        button.setText("Wybierz folder");
        button.setBackground(getDrawable(R.drawable.gradient));
        button.setTextSize(20);
        button.setTextColor(getColor(R.color.colorText));
        button.setHeight(70);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setPadding(40, 20, 20, 20);
        scrollLayout.addView(button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //the whole folder choosing magic happens here i guess

                pickDirectory();

                scrollLayout.removeAllViews();

                firstTime = false;

                appearifySeries();
            }
        });

    }

    void loadContent()
    {
        Series temp;
        List<String> temp2 = new ArrayList<String>();
        comics.clear();
        File directory = new File(Environment.getExternalStorageDirectory().toString() + comicDir);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                File[] files = directory.listFiles();
                File[] issues;

                for (File file : files)
                {
                    temp = new Series();
                    temp2.clear();
                    issues = file.listFiles();
                    temp.title = file.getName();
                    for (File issue : issues)
                    {
                        temp2.add(issue.getName());
                    }
                    temp.issues = temp2;
                    comics.add(temp);
                }
            } catch (Exception error)
            {
                Log.e("Error", error.getMessage());
            }
        }

        Log.e("FOLDER CONT: ", String.valueOf(comics.size()));
        for(Series ser : comics)
        {
            Log.e("INFO", ser.title + ", " + ser.issues.toString());
        }
    }

    void pickDirectory()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

//            ActivityCompat.requestPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, ); figure this out
        }

    }

    void goToChapter(int number)
    {
        Intent chapter = new Intent(this, ChapterActivity.class);
        startActivityForResult(chapter, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            if (opened != null)
            {
                if ((newLL.indexOfChild(opened) % 2 == 0))
                {
                    opened.setBackgroundColor(getColor(R.color.colorAccent));
                } else
                {
                    opened.setBackgroundColor(getColor(R.color.colorAccent2));
                }
                opened = null;
            }
        }
    }

    public void goToSettings(View view)
    {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }
}