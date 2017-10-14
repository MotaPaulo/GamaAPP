package com.android.pena.david.projetoteste;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {


    @BindView(R.id.imBackground)
    ImageView mImBackground;
    @BindView(R.id.movieTitle)
    TextView mMovieTitle;
    @BindView(R.id.movieYearText)
    TextView mMovieYearText;
    @BindView(R.id.movieRateText)
    TextView mMovieRateText;
    @BindView(R.id.movieDescText)
    TextView mMovieDescText;

    private String title;
    private String banner;
    private String desc;
    private String year;
    private String rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);



        //Adiciona backbutton para voltar para o pai
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //pega a string que veio da intenção
        String obj = getIntent().getStringExtra("CATEGORIA_TAG");

        title = getIntent().getStringExtra("TITLE_TAG");
        banner = getIntent().getStringExtra("BANNER_TAG");
        desc = getIntent().getStringExtra("DESC_TAG");
        year = getIntent().getStringExtra("ANO_TAG");
        rate = getIntent().getStringExtra("RATE_TAG");

        setTitle(title);
        if (obj != null) {
            setText();
        }

    }

    private void setText() {

        mMovieTitle.setText(title);
        Picasso.with(this).load(banner)
                .into(mImBackground);
        mMovieYearText.setText(year);
        mMovieRateText.setText(rate);
        mMovieDescText.setText(desc);


    }
}
