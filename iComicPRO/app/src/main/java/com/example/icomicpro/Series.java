package com.example.icomicpro;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Series implements Serializable
{


    String title;
    List<String> issues = new ArrayList<>();
}
