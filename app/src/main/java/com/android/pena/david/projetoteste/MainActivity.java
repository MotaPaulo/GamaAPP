package com.android.pena.david.projetoteste;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private String move_id = "1";
    private String MOVIES_DB_URL =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=eb0082822481806576e93cc93ad2e1f8&language=en-US&page=1";
    private static final String API_KEY_VALUE = "eb0082822481806576e93cc93ad2e1f8";
    @BindView(R.id.lista_categorias)
    protected RecyclerView mListaCategorias;



    //Cria a activity e executa a chamada à API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("TOP FILMES");
        new getCategoriesAsync().execute();
    }



    private class getCategoriesAsync extends AsyncTask<Void,Void,String>{

        //Metodo Asincrono para fazer a chamada da API
        @Override
        protected String doInBackground(Void... voids) {

            try{
                Uri builtUri = Uri.parse(MOVIES_DB_URL).buildUpon()
                        .appendQueryParameter("api_key",API_KEY_VALUE).build();

                URL url = new URL(builtUri.toString());
                String response = getResponseFromHttpUrl(url);
                return response;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("filme",s);
            if(s == null) return;

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray array = new JSONArray(obj.getString("results"));
                mListaCategorias.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                CategoriasAdapter adapter = new CategoriasAdapter(getApplicationContext(), array);
                mListaCategorias.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo que gera uma conexão HTTP e processa o inputStream em uma String
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
