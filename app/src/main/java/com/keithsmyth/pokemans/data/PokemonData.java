package com.keithsmyth.pokemans.data;

import com.keithsmyth.pokemans.model.Party;
import com.keithsmyth.pokemans.model.PokeType;
import com.keithsmyth.pokemans.model.Pokedex;
import com.keithsmyth.pokemans.model.Pokemon;

/**
 * @author keithsmyth
 */
public interface PokemonData {
  public void getPokedex(Callback<Pokedex> callback);

  public void getPokemon(String uri, Callback<Pokemon> callback);

  public void getPokeType(String uri, Callback<PokeType> callback);

  public void getParty(Callback<Party> callback);

  public void addToParty(Party.Member member);
}
