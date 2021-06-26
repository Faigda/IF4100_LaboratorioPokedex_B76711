package com.example.if4100_laboratoriopokedex_b76711;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.if4100_laboratoriopokedex_b76711.models.Pokemon;
import com.example.if4100_laboratoriopokedex_b76711.models.PokemonAPI;
import com.example.if4100_laboratoriopokedex_b76711.pokeapi.PokeAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "IF4100_LaboratorioPokedex_B76711";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    private int offset;

    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(aptoParaCargar){
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }

                }
            }
        });

        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        aptoParaCargar = true;

        offset = 0;

        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset){
        PokeAPI service = retrofit.create(PokeAPI.class);
        Call<PokemonAPI> pokemonRespuestaCall = service.obtenerListaPokemon(20, offset);

        pokemonRespuestaCall.enqueue(new Callback<PokemonAPI>() {
            @Override
            public void onResponse(Call<PokemonAPI> call, Response<PokemonAPI> response) {
                aptoParaCargar = true;
                if(response.isSuccessful()){
                    PokemonAPI pokemonAPI = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonAPI.getResults();

                    pokemonAdapter.adicionarListaPokemon(listaPokemon);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonAPI> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}