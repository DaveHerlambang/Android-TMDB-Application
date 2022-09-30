package com.example.androidmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.example.androidmobile.Adapter.spanCountOne;
import static com.example.androidmobile.Adapter.spanCountThree;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemListener {


    private static String JSON_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=bb2c0e870b5b334c903b55c7a3bebaf1";
    List<Movies> movieList;
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        adapter = new Adapter(getApplicationContext(), movieList, gridLayoutManager, this);

        if(isNetworkAvailable()){
            GetData getData = new GetData();
            getData.execute();
        }

    }

    //movie click listener
    @Override
    public void onItemClick(int position) {
        Intent moveToDetail = new Intent(this, DetailActivity.class);
        moveToDetail.putExtra("ID", movieList.get(position).getId());
        moveToDetail.putExtra("TITLE", movieList.get(position).getName());
        moveToDetail.putExtra("IMG", movieList.get(position).getImg());
        moveToDetail.putExtra("DATE", movieList.get(position).getDate());
        moveToDetail.putExtra("OVERVIEW", movieList.get(position).getOverview());
        moveToDetail.putExtra("RATING", movieList.get(position).getRating());
        startActivity(moveToDetail);
    }


    //Check Connection
    public boolean isNetworkAvailable(){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService((Context.CONNECTIVITY_SERVICE));
            NetworkInfo networkInfo = null;
            if(connectivityManager != null){
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }catch (NullPointerException e){
            return false;
        }
    }

    //read API
    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current= "";

            try{
                URL url;
                HttpURLConnection urlConnection = null;
                try{
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Movies model = new Movies();
                    model.setId(jsonObject1.getString("id"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    model.setDate(jsonObject1.getString("release_date"));
                    model.setOverview(jsonObject1.getString("overview"));
                    model.setRating(jsonObject1.getString("vote_average"));

                    movieList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            PutData(movieList);
        }
    }

    private void  PutData(List<Movies> movieList){
        gridLayoutManager = new GridLayoutManager(this, spanCountOne, GridLayoutManager.VERTICAL,false);
        Adapter adapter = new Adapter(this,movieList,gridLayoutManager,this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.switchView){
            switchLayout();
            switchIcon(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchLayout(){
        if(gridLayoutManager.getSpanCount() == spanCountOne){
            gridLayoutManager.setSpanCount(spanCountThree);
        }else{
            gridLayoutManager.setSpanCount(spanCountOne);
        }
        adapter.notifyItemRangeChanged(0,adapter.getItemCount());
    }

    private void switchIcon(MenuItem item){
        if(gridLayoutManager.getSpanCount() == spanCountThree){
            item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_menu_24));
        }else{
            item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_grid_on_24));
        }
    }

    public void moveToProfile(View view) {
        Intent moveProfile = new Intent(this, ProfileActivity.class);
        view.getContext().startActivity(moveProfile);
    }
}