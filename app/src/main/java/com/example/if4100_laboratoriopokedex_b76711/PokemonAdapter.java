package com.example.if4100_laboratoriopokedex_b76711;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.if4100_laboratoriopokedex_b76711.models.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataset;

    private Context context;

    private View.OnClickListener listener;

    public PokemonAdapter(Context context){
        dataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pokemon, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nombrePokemon.setText("#" + p.getNumber() + " - " + p.getName());

        holder.setOnclickListener(p.getNumber());

        Glide.with(this.context)
        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.fotoPokemon);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView fotoPokemon;
        public TextView nombrePokemon;
        public int numeroPokemon;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoPokemon = (ImageView) itemView.findViewById(R.id.fotoPokemon);
            nombrePokemon = (TextView) itemView.findViewById(R.id.nombrePokemon);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), InformacionPokemonActivity.class);
            intent.putExtra("idPokemon", this.numeroPokemon);
            view.getContext().startActivity(intent);
        }

        public void setOnclickListener(int numeroPokemon){
            fotoPokemon.setOnClickListener(this);
            this.numeroPokemon = numeroPokemon;
        }
    }

}
