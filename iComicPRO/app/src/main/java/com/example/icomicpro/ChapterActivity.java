package com.example.icomicpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ChapterActivity extends AppCompatActivity
{
    String folder;
    String tempDir;

    Series opened = null;
    int chapter;

    Bitmap[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        opened = (Series) getIntent().getSerializableExtra("series");
        chapter = getIntent().getIntExtra("chapter", 0);
        folder = getIntent().getStringExtra("directory");

        if (opened != null && chapter >= 0)
        {
            if (chapter < opened.issues.size()) unzipData();
            else handleInvalidData();
        } else handleInvalidData();
    }

    void handleInvalidData()
    {
        Log.e("INVALID DATA", "OUT OF BOUNDS");
    }

    String getPageName(int value)
    {
        if(value < 10) return "00" + String.valueOf(value);
        if(value < 100) return "0" + String.valueOf(value);
        return String.valueOf(value);
    }

    int getPageIndex(String value)
    {
        if((String.valueOf(value.charAt(0)).equals("0")) && (String.valueOf(value.charAt(1)).equals("0")))
        {
            return Integer.parseInt(String.valueOf(value.charAt(2)));
        }

        if(String.valueOf(value.charAt(0)).equals("0"))
        {
            return 10 * Integer.parseInt(String.valueOf(value.charAt(1))) + Integer.parseInt(String.valueOf(value.charAt(2)));
        }

        return Integer.parseInt(value);
    }

    void unzipData()
    {
        Context context = getApplicationContext();
        String path = folder + File.separator + opened.title + File.separator
                + opened.issues.get(chapter) + ".cbz";

        tempDir = context.getExternalFilesDir(null) + File.separator + ".Temporary";
        File temp = new File(tempDir);

        if (temp.exists())
        {
            for(File file : Objects.requireNonNull(temp.listFiles()))
            {
                file.delete();
            }
        }
        else
        {
            if (!temp.mkdirs())
            {
                handleInvalidData();
                return;
            }
        }

        File zipfile = new File(context.getExternalFilesDir(null), path);

        try
        {
            int counter = 0;
            String extension;

            FileInputStream file = new FileInputStream(zipfile);
            ZipInputStream issue = new ZipInputStream(file);
            ZipEntry content = null;

            while ((content = issue.getNextEntry()) != null)
            {
                extension = content.getName().split("[.]")[1];
                if (extension.contains("jpg"))
                {

                    FileOutputStream unzipped = new FileOutputStream(temp + File.separator +
                            "p" + getPageName(counter) + "." + extension);
                    BufferedOutputStream marine = new BufferedOutputStream(unzipped);
                    byte[] buffer = new byte[1024];

                    int read;
                    while ((read = issue.read(buffer)) != -1)
                    {
                        marine.write(buffer, 0, read);
                    }

                    marine.close();
                    issue.closeEntry();
                    unzipped.close();

                    counter++;
                }
            }

            loadImages(temp);
        } catch (Exception error)
        {
            Log.e("ERROR", "Zip error!");
            Log.e("Error", error.getMessage());
            handleInvalidData();
        }
    }

    void loadImages(File temp)
    {
        int index;
        String stringValue;

        images = new Bitmap[Objects.requireNonNull(temp.listFiles()).length];

        for(File image : Objects.requireNonNull(temp.listFiles()))
        {
            stringValue = image.getName().split("[.]")[0];
            stringValue = stringValue.replace("p", "");
            index = getPageIndex(stringValue);

            stringValue = image.getAbsolutePath();

            images[index] = BitmapFactory.decodeFile(stringValue);
        }

        displayPages();
    }

    void displayPages()
    {
        final LinearLayout scrollLayout = findViewById(R.id.comicView);

        scrollLayout.removeAllViews();

        for(Bitmap page : images)
        {
            ImageView newPage = new ImageView(this);
            newPage.setImageBitmap(page);

            scrollLayout.addView(newPage);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        File temp = new File(tempDir);
        for(File file : Objects.requireNonNull(temp.listFiles()))
        {
            file.delete();
        }

        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }
}