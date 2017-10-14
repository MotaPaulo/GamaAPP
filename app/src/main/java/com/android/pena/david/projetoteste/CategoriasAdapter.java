package com.android.pena.david.projetoteste;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by david on 13/10/17.
 */

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder> {

    private JSONArray categorias;
    private Context context;

    //recebe o context e a lista de categorias
    public CategoriasAdapter(Context pContext,JSONArray pCategorias) {
        this.categorias = pCategorias;
        this.context = pContext;
    }

    //cria o espaço onde será exibida a view da linha
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_row, parent, false);
        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    //referencia um dado a uma determinada linha da lista
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.bindData(categorias.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //retorna o tamanho da lista
    @Override
    public int getItemCount() {
        return categorias.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private String imageString = "https://image.tmdb.org/t/p/w500";

        private TextView categoryName;
        private ImageView moveImage;
        private CardView cardView;
        private JSONObject categoria;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoria_name);
            cardView = (CardView) itemView.findViewById(R.id.categoria_cardview);
            moveImage = (ImageView) itemView.findViewById(R.id.iVMainLogo);
            cardView.setOnClickListener(this);

        }
        //pega os dados do Adapter e adiciona na viu
        public void bindData(JSONObject category){
            categoria = category;
            try {
                categoryName.setText(category.getString("title"));
                Picasso.with(context).load(imageString.concat(category.getString("poster_path")))
                        .into(moveImage);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Lê um click
        @Override
        public void onClick(View view) {
            Intent it = new Intent(context, Main2Activity.class);
            it.putExtra("CATEGORIA_TAG",categoria.toString());
            try {
                it.putExtra("TITLE_TAG",categoria.getString("title"));
                it.putExtra("BANNER_TAG","https://image.tmdb.org/t/p/w500"+
                        categoria.getString("backdrop_path"));
                it.putExtra("ANO_TAG",categoria.getString("release_date"));
                it.putExtra("DESC_TAG",categoria.getString("overview"));
                it.putExtra("RATE_TAG",categoria.getString("vote_average"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        }
    }

}
