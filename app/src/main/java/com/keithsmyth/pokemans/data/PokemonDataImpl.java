package com.keithsmyth.pokemans.data;

import com.keithsmyth.pokemans.api.PokemonService;
import com.keithsmyth.pokemans.model.Party;
import com.keithsmyth.pokemans.model.PokeType;
import com.keithsmyth.pokemans.model.Pokedex;
import com.keithsmyth.pokemans.model.Pokemon;

import java.util.HashMap;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author keithsmyth
 */
public final class PokemonDataImpl implements PokemonData {

  public static final String GENERIC_ERROR = "There was an errors";

  private static Pokedex pokedex;
  private static HashMap<String, Pokemon> pokemonLookup = new HashMap<>();
  private static HashMap<String, PokeType> pokeTypeLookup = new HashMap<>();

  private final PokemonService pokemonService;
  private final PreferenceWrapper preferenceWrapper;

  public PokemonDataImpl(PokemonService pokemonService, PreferenceWrapper preferenceWrapper) {
    this.pokemonService = pokemonService;
    this.preferenceWrapper = preferenceWrapper;
  }

  @Override public void getPokedex(final Callback<Pokedex> callback) {
    // cache
    if (pokedex != null) {
      callback.onSuccess(pokedex);
      return;
    }

    // api
    pokemonService.getPokedex(new retrofit.Callback<Pokedex>() {
      @Override public void success(Pokedex pokedex, Response response) {
        PokemonDataImpl.pokedex = pokedex;
        callback.onSuccess(pokedex);
      }

      @Override public void failure(RetrofitError error) {
        callback.onFail(error != null ? error.getMessage() : GENERIC_ERROR);
      }
    });
  }

  @Override public void getPokemon(final String uri, final Callback<Pokemon> callback) {
    // cache
    if (pokemonLookup.containsKey(uri)) {
      callback.onSuccess(pokemonLookup.get(uri));
      return;
    }

    // api
    pokemonService.getPokemon(uri, new retrofit.Callback<Pokemon>() {
      @Override public void success(Pokemon pokemon, Response response) {
        pokemonLookup.put(uri, pokemon);
        callback.onSuccess(pokemon);
      }

      @Override public void failure(RetrofitError error) {
        callback.onFail(error != null ? error.getMessage() : GENERIC_ERROR);
      }
    });
  }

  @Override public void getPokeType(final String uri, final Callback<PokeType> callback) {
    // cache
    if (pokeTypeLookup.containsKey(uri)) {
      callback.onSuccess(pokeTypeLookup.get(uri));
      return;
    }

    // api
    pokemonService.getPokeType(uri, new retrofit.Callback<PokeType>() {
      @Override public void success(PokeType pokeType, Response response) {
        pokeTypeLookup.put(uri, pokeType);
        callback.onSuccess(pokeType);
      }

      @Override public void failure(RetrofitError error) {
        callback.onFail(error != null ? error.getMessage() : GENERIC_ERROR);
      }
    });
  }

  @Override public void getParty(Callback<Party> callback) {
    String partyString = preferenceWrapper.getPartyList();
    callback.onSuccess(Party.fromJson(partyString));
  }

  @Override public void addToParty(Party.Member member) {
    String partyString = preferenceWrapper.getPartyList();
    Party party = Party.fromJson(partyString);
    party.memberList.add(member);
    preferenceWrapper.setPartyList(party.getAsJson());
  }

  @Override public void clearParty() {
    preferenceWrapper.setPartyList(null);
  }
}
