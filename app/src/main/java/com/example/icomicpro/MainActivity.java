package com.example.icomicpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout scrollLayout = findViewById(R.id.linearMain);

        scrollLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        TextView testOne = new TextView(this);
        testOne.setText("jakis tekst");
        testOne.setClickable(true);
        scrollLayout.addView(testOne);

        Button button = new Button(this);
        button.setText("blah blah nazwa serii");
        button.setBackgroundColor(Color.WHITE);
        button.setHeight(70);
        button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button.setPadding(40,20,20,20);
        scrollLayout.addView(button);

        final Button button2 = new Button(this);
        button2.setText("ok ale jak wyglada jeden pod drugim");
        button2.setBackgroundColor(Color.WHITE);
        button2.setHeight(70);
        button2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button2.setPadding(40,20,20,20);
        scrollLayout.addView(button2);

        final ScrollView newSV = new ScrollView(this);
        final LinearLayout newLL = new LinearLayout(this);
        newLL.setOrientation(LinearLayout.VERTICAL);
        newSV.addView(newLL);

        scrollLayout.addView(newSV, scrollLayout.indexOfChild(button2)+1);

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView letssee = new TextView(v.getContext());
                letssee.setText("haha");
                newLL.addView(letssee);

                button2.setText("haha zmienilam ci tekst frajerze");

            }
        });

        Button button3 = new Button(this);
        button3.setText("aw fuck zdaje sie ze one sie sklejaja trzeba to jakos naprawic");
        button3.setBackgroundColor(Color.WHITE);
        button3.setHeight(70);
        button3.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button3.setPadding(40,20,20,20);
        scrollLayout.addView(button3);

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(newSV.getVisibility() == View.GONE)
                {
                    newSV.setVisibility(View.VISIBLE);
                    button2.setText("pojawiam sie "+newSV.getVisibility());
                }
                else
                {
                    newSV.setVisibility(View.GONE);
                    button2.setText("i znikam "+newSV.getVisibility());
                }
            }
        });
    }
}