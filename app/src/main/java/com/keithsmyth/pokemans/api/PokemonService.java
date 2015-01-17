package com.keithsmyth.pokemans.api;

import com.keithsmyth.pokemans.model.PokeType;
import com.keithsmyth.pokemans.model.Pokedex;
import com.keithsmyth.pokemans.model.Pokemon;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author keithsmyth
 */
public interface PokemonService {
  @GET("/api/v1/pokedex/1") void getPokedex(Callback<Pokedex> callback);

  @GET("/{uri}") void getPokemon(@Path(value = "uri", encode = false) String uri,
                                 Callback<Pokemon> callback);

  @GET("/api/v1/pokemon/{id}") void getPokemon(@Path(value = "id") Long id, Callback<Pokemon> callback);

  @GET("/{uri}") void getPokeType(@Path(value = "uri", encode = false) String uri,
                                  Callback<PokeType> callback);
}
