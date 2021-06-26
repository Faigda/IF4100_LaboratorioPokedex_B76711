package com.example.if4100_laboratoriopokedex_b76711.pokeapi;

import com.example.if4100_laboratoriopokedex_b76711.InformacionPokemon;
import com.example.if4100_laboratoriopokedex_b76711.models.PokemonAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeAPI {

    @GET("pokemon")
    Call<PokemonAPI> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<InformacionPokemon> obtenerInformacionPokemon(@Path("id") int id);

}
