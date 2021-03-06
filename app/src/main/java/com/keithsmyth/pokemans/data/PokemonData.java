package com.keithsmyth.pokemans.data;

import com.keithsmyth.pokemans.model.Move;
import com.keithsmyth.pokemans.model.Party;
import com.keithsmyth.pokemans.model.PartyMember;
import com.keithsmyth.pokemans.model.PokeType;
import com.keithsmyth.pokemans.model.Pokedex;
import com.keithsmyth.pokemans.model.Pokemon;
import com.keithsmyth.pokemans.model.Sprite;

/**
 * @author keithsmyth
 */
public interface PokemonData {
  public void getPokedex(Callback<Pokedex> callback);

  public void getPokemon(String uri, Callback<Pokemon> callback);

  public void getPokemon(long id, Callback<Pokemon> callback);

  public void getPokeType(String uri, Callback<PokeType> callback);

  public void getParty(Callback<Party> callback);

  public void addToParty(PartyMember member);

  public void clearParty();

  public void getMove(String uri, Callback<Move> callback);

  public void getSprite(String uri, Callback<Sprite> callback);
}
