package com.example.ranyass.newsapp_stage1;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    JSONArray jsonArray;
    HashMap<Integer, ArrayList<String>> moviesHashMap;
    ArrayList<Bitmap> bitmapArray = new ArrayList();
    Bitmap Imagebitmap;
    ProgressDialog dialog;

    private ListView listView;

    private Context context;

    public fetchData(ListView listView,Context context){
        this. listView = listView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        moviesHashMap = new HashMap<>();
        try
        {

            URL url = new URL("https://newsapi.org/v2/top-headlines?country=us&apiKey=d85428d6106547e880e7ca7c43f3c210");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";

            while(line!=null)
            {
              line = bufferedReader.readLine();
              data = data + line;
            }

            JSONObject jsonObject = new JSONObject(data);
            jsonArray = jsonObject.getJSONArray("articles");

            for(int i=0;i<jsonArray.length();i++)
            {
                final ArrayList<String> appleDefs = new ArrayList<>();
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                appleDefs.add(jsonObject2.getString("title").toString());
                appleDefs.add(jsonObject2.getString("description").toString());
                appleDefs.add(jsonObject2.getString("publishedAt").toString());
                moviesHashMap.put(i, appleDefs);

                try {
                    Imagebitmap = BitmapFactory.decodeStream((InputStream)new URL(jsonObject2.getString("urlToImage").toString()).getContent());
                } catch (Exception e) {
                    Imagebitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.noimg);
                    e.printStackTrace();
                }
                bitmapArray.add(Imagebitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        dialog.dismiss();

        CustomAdapter CustomAdapter = new CustomAdapter(context.getApplicationContext(),moviesHashMap,bitmapArray);
        listView.setAdapter(CustomAdapter);
    }
}
