package com.example.icomicpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity
{
    boolean firstTime = true; //temporary
    TextView opened = null;

    final String PATH = "Comics";

    ArrayList<Series> comics = new ArrayList<Series>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (firstTime)
        {
            Log.e("First time", "entered");
            if (loadComics()) appearifySeries();
            else handleCriticalErrors();

            firstTime = false;
        } else appearifySeries();
    }

    boolean loadComics()
    {
        Context context = getApplicationContext();
        Series temp;
        File[] temp2;
        List<String> temp4 = new ArrayList<>();

        File comicDir = new File(context.getExternalFilesDir(null), PATH);

        comics.clear();

        if (!comicDir.exists())
        {
            if (!comicDir.mkdirs()) return false;
        }

        File[] files = comicDir.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    temp = new Series();
                    temp.title = file.getName();
                    temp2 = file.listFiles();
                    if (temp2 != null)
                    {
                        temp4.clear();
                        for (File temp3 : temp2)
                        {
                            if (!temp3.isDirectory())
                            {
                                temp4.add(temp3.getName().split("[.]")[0]);
                            }
                        }
                        temp.issues.addAll(temp4);
                    }
                    comics.add(temp);
                }
            }
        }

        for (Series ser : comics)
        {
            Log.e("Contents: ", ser.title + " " + String.valueOf(ser.issues.size()));
        }

        return true;
    }

    void handleCriticalErrors()
    {
        //todo
    }

    void appearifySeries()
    {
        final LinearLayout scrollLayout = findViewById(R.id.linearMain);

        scrollLayout.setPadding(40, 40, 40, 40);

        for(Series series : comics)
        {
            final Button comic = new Button(this);
            comic.setText(series.title);
            comic.setBackground(getDrawable(R.drawable.gradient));
            comic.setTextSize(20);
            comic.setTextColor(getColor(R.color.colorText));
            comic.setHeight(70);
            comic.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            comic.setPadding(40, 20, 20, 20);
            scrollLayout.addView(comic);

            final ScrollView newSV = new ScrollView(this);
            final LinearLayout newLL = new LinearLayout(this);
            newLL.setOrientation(LinearLayout.VERTICAL);
            newLL.setBackgroundColor(getColor(R.color.colorAccent));
            newSV.setVisibility(View.GONE);
            newSV.addView(newLL);

            if (!series.issues.isEmpty())
            {
                for(String title : series.issues)
                {
                    final TextView letssee = new TextView(comic.getContext());
                    letssee.setText(title);
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
                }
            }
            else
            {
                TextView emptyFolder = new TextView(comic.getContext());
                emptyFolder.setText(R.string.resource_error);
                emptyFolder.setTextSize(16);
                emptyFolder.setTextColor(getColor(R.color.colorError));
                emptyFolder.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                emptyFolder.setPadding(0, 20, 0, 20);
                newLL.addView(emptyFolder);
            }

            scrollLayout.addView(newSV, scrollLayout.indexOfChild(comic) + 1); //fix this adding a new divider!!
            comic.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (newSV.getVisibility() == View.GONE)
                    {
                        newSV.setVisibility(View.VISIBLE);
                    } else
                    {
                        newSV.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public void refreshApp(View view)
    {
        LinearLayout scrollLayout = findViewById(R.id.linearMain);
        scrollLayout.removeAllViews();
        comics.clear();
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

                scrollLayout.removeAllViews();

                firstTime = false;

                appearifySeries();
            }
        });

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
            /*if (opened != null)
            {
                if ((newLL.indexOfChild(opened) % 2 == 0))
                {
                    opened.setBackgroundColor(getColor(R.color.colorAccent));
                } else
                {
                    opened.setBackgroundColor(getColor(R.color.colorAccent2));
                }
                opened = null;
            }*/
        }
    }

    public void goToSettings(View view)
    {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }
}