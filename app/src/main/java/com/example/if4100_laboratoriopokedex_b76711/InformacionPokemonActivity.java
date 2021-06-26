package com.example.if4100_laboratoriopokedex_b76711;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewOnReceiveContentListener;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.if4100_laboratoriopokedex_b76711.models.Pokemon;
import com.example.if4100_laboratoriopokedex_b76711.models.PokemonAPI;
import com.example.if4100_laboratoriopokedex_b76711.pokeapi.PokeAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformacionPokemonActivity extends AppCompatActivity {

    public TextView text_ViewidPokemon;
    public TextView text_ViewNombrePokemon;
    public TextView text_ViewAltura;
    public TextView text_ViewAnchura;
    public ImageView image_ViewFotoPokemon;

    public Context context;

    private Retrofit retrofit;

    private static final String TAG = "IF4100_LaboratorioPokedex_B76711";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_pokemon);
        this.text_ViewidPokemon = (TextView) findViewById(R.id.idPokemon);
        this.text_ViewNombrePokemon = (TextView) findViewById(R.id.nombrePokemon);
        this.image_ViewFotoPokemon = (ImageView) findViewById(R.id.fotoPokemon);
        this.text_ViewAltura = (TextView) findViewById(R.id.alturaPokemon);
        this.text_ViewAnchura = (TextView) findViewById(R.id.anchuraPokemon);
        Bundle informacionExtra = getIntent().getExtras();

        int idPokemon = informacionExtra.getInt("idPokemon");
        this.text_ViewidPokemon.setText(String.valueOf(idPokemon));

        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        this.context = this;

        obtenerDatos(idPokemon);
    }

    private void obtenerDatos(int id){
        PokeAPI service = retrofit.create(PokeAPI.class);
        Call<InformacionPokemon> pokemonRespuestaCall = service.obtenerInformacionPokemon(id);

        pokemonRespuestaCall.enqueue(new Callback<InformacionPokemon>() {
            @Override
            public void onResponse(Call<InformacionPokemon> call, Response<InformacionPokemon> response) {
                if(response.isSuccessful()){
                    InformacionPokemon informacionPokemon = response.body();
                    text_ViewNombrePokemon.setText(informacionPokemon.getName());
                    Glide.with(context)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + informacionPokemon.getId() + ".png")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_ViewFotoPokemon);
                    text_ViewAnchura.setText(String.valueOf(informacionPokemon.getWeight()));
                    text_ViewAltura.setText(String.valueOf(informacionPokemon.getHeight()));
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<InformacionPokemon> call, Throwable t) {

            }
        });
    }
}