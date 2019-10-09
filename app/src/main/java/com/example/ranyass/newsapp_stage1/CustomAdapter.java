package com.example.ranyass.newsapp_stage1;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends BaseAdapter {

    HashMap<Integer, ArrayList<String>> newsHashMap;
    ArrayList<Bitmap> bitmapArray;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext,HashMap newsHashMap,ArrayList<Bitmap> bitmapArray) {
        inflter = (LayoutInflater.from(applicationContext));
        this.newsHashMap = newsHashMap;
        this.bitmapArray = bitmapArray;
    }

    @Override
    public int getCount() {
        return newsHashMap.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview_item, null);
        TextView texArticleName = (TextView) view.findViewById(R.id.texArticleName);
        texArticleName.setText(newsHashMap.get(i).get(0).toString());

        TextView texArticleOverview = (TextView) view.findViewById(R.id.texArticleOverview);
        texArticleOverview.setText(newsHashMap.get(i).get(1).toString());

        TextView texReleaseDate = (TextView) view.findViewById(R.id.texReleaseDate);
        texReleaseDate.setText(newsHashMap.get(i).get(2).toString());

        ImageView imgArticlePoster = (ImageView) view.findViewById(R.id.imgArticlePoster);
        imgArticlePoster.setImageBitmap(bitmapArray.get(i));

        return view;
    }

}
