package com.example.icomicpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;;
import java.util.zip.ZipInputStream;

public class ChapterActivity extends AppCompatActivity
{
    String folder;
    String tempDir;
    String chapterName = "Unknown";

    Series opened = null;

    boolean fullscreen;

    int chapter;

    Bitmap[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        if(savedInstanceState != null)
        {
            images = (Bitmap[]) savedInstanceState.getSerializable("images");
            fullscreen = savedInstanceState.getBoolean("fullscreen", true);
            chapterName = savedInstanceState.getString("title", chapterName);
            chapter = savedInstanceState.getInt("chapter", 0);
            opened = (Series) savedInstanceState.getSerializable("series");
            displayPages();
        }
        else
        {
            SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
            fullscreen = preferences.getBoolean("fullscreen", true);

            opened = (Series) getIntent().getSerializableExtra("series");
            chapter = getIntent().getIntExtra("chapter", 0);
            folder = getIntent().getStringExtra("directory");

            if (opened != null && chapter >= 0)
            {
                if (chapter < opened.issues.size())
                {
                    chapterName = opened.title + " " + opened.issues.get(chapter);
                    unzipData();
                }
                else handleInvalidData();
            } else handleInvalidData();
        }

        if (fullscreen) fullScreen();
    }

    void fullScreen()
    {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable("images", images);
        outState.putBoolean("fullscreen", fullscreen);
        outState.putString("title", chapterName);
        outState.putInt("chapter", chapter);
        outState.putSerializable("series", opened);
        super.onSaveInstanceState(outState);
    }

    public void goNext(View view)
    {
        if((chapter + 1) < opened.issues.size())
        {
            chapter++;
            chapterName = opened.title + " " + opened.issues.get(chapter);

            unzipData();
        }
        else
        {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.last_chapter, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void goBack(View view)
    {
        if((chapter - 1) >= 0)
        {
            chapter--;
            chapterName = opened.title + " " + opened.issues.get(chapter);

            unzipData();
        }
        else
        {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.first_chapter, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    void handleInvalidData()
    {
        Log.e("INVALID DATA", "OUT OF BOUNDS");
    }

    String getPageName(int value)
    {
        if (value < 10) return "00" + String.valueOf(value);
        if (value < 100) return "0" + String.valueOf(value);
        return String.valueOf(value);
    }

    int getPageIndex(String value)
    {
        if ((String.valueOf(value.charAt(0)).equals("0")) && (String.valueOf(value.charAt(1)).equals("0")))
        {
            return Integer.parseInt(String.valueOf(value.charAt(2)));
        }

        if (String.valueOf(value.charAt(0)).equals("0"))
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
            for (File file : Objects.requireNonNull(temp.listFiles()))
            {
                file.delete();
            }
        } else
        {
            if (!temp.mkdirs())
            {
                handleInvalidData();
                return;
            }
        }

        File zipFile = new File(context.getExternalFilesDir(null), path);

        try
        {
            int counter = 0;
            String extension;

            FileInputStream file = new FileInputStream(zipFile);
            ZipInputStream issue = new ZipInputStream(file);
            ZipEntry content = null;

            while ((content = issue.getNextEntry()) != null)
            {
                extension = content.getName().split("[.]")[1];
                if (extension.contains("jpg") || extension.contains("JPG"))
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

        for (File image : Objects.requireNonNull(temp.listFiles()))
        {
            stringValue = image.getName().split("[.]")[0];
            stringValue = stringValue.replace("p", "");
            index = getPageIndex(stringValue);

            stringValue = image.getAbsolutePath();

            images[index] = BitmapFactory.decodeFile(stringValue);
        }

        for (File file : Objects.requireNonNull(temp.listFiles()))
        {
            file.delete();
        }

        displayPages();
    }

    @SuppressLint("ClickableViewAccessibility")
    void displayPages()
    {
        ScrollView sw = findViewById(R.id.primaryScroll);
        LinearLayout scrollLayout = findViewById(R.id.comicView);

        scrollLayout.removeAllViews();

        addButtons(scrollLayout);
        for (Bitmap page : images)
        {
            ImageView newPage = new ImageView(this);
            newPage.setScaleType(ImageView.ScaleType.FIT_XY);
            newPage.setAdjustViewBounds(true);
            newPage.setImageBitmap(page);

            scrollLayout.addView(newPage);
            //break;
        }
        addButtons(scrollLayout);
    }

    public void addButtons(LinearLayout layout)
    {
        LinearLayout horizontal = new LinearLayout(this);
        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        horizontal.setMinimumHeight(50);
        horizontal.setBackground(getDrawable(R.drawable.gradient));
        horizontal.setGravity(Gravity.CENTER);

        ImageButton back = new ImageButton(this);
        back.setImageDrawable(getDrawable(R.drawable.arrow_back));
        back.setBackgroundColor(getColor(R.color.colorPrimary));;
        back.setPadding(40, 20, 20, 20);
        back.setOnClickListener(this::goBack);

        TextView text = new TextView(this);
        text.setText(chapterName);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(getColor(R.color.colorText));

        ImageButton forward = new ImageButton(this);
        forward.setImageDrawable(getDrawable(R.drawable.next_chapter));
        forward.setBackgroundColor(getColor(R.color.colorPrimary));
        forward.setPadding(40, 20, 20, 20);
        forward.setOnClickListener(this::goNext);

        horizontal.addView(back);
        horizontal.addView(text);
        horizontal.addView(forward);

        layout.addView(horizontal);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }
}