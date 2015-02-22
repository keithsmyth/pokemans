package com.keithsmyth.pokemans;

import android.app.Application;

import com.keithsmyth.pokemans.api.PokemonService;
import com.keithsmyth.pokemans.data.PokemonData;
import com.keithsmyth.pokemans.data.PokemonDataImpl;
import com.keithsmyth.pokemans.data.PreferenceWrapper;

import retrofit.RestAdapter;

/**
 * @author keithsmyth
 */
public class App extends Application {

  public static final String ENDPOINT = "http://pokeapi.co/";
  private static App instance;
  private static PokemonData pokemonData;

  public static PokemonData getPokemonData() {
    if (pokemonData == null) {
      PokemonService pokemonService = new RestAdapter.Builder()
          .setEndpoint(ENDPOINT)
          .build()
          .create(PokemonService.class);
      PreferenceWrapper preferenceWrapper = new PreferenceWrapper(instance.getApplicationContext());
      pokemonData = new PokemonDataImpl(pokemonService, preferenceWrapper);
    }
    return pokemonData;
  }

  public static String getResourceString(int stringId) {
    return instance.getString(stringId);
  }

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
  }
}
