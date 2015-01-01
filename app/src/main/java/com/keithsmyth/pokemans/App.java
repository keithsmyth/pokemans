package com.keithsmyth.pokemans;

import android.app.Application;

import com.keithsmyth.pokemans.data.PokemonData;
import com.keithsmyth.pokemans.data.PokemonDataImpl;

/**
 * @author keithsmyth
 */
public class App extends Application {

  private static PokemonData pokemonData;

  public static PokemonData getPokemonData() {
    if (pokemonData == null) {
      pokemonData = new PokemonDataImpl();
    }
    return pokemonData;
  }
}
