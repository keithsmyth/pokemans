package com.keithsmyth.pokemans.model;

import com.google.gson.Gson;

/**
* @author keithsmyth
*/
public class PartyMember {

  public static PartyMember fromPokemon(Pokedex.Pokemon pokemon) {
    PartyMember member = new PartyMember();
    member.id = pokemon.retrieveId();
    member.name = pokemon.name;
    return member;
  }

  public static PartyMember fromJson(String json) {
    return json == null ? new PartyMember() : new Gson().fromJson(json, PartyMember.class);
  }

  public long id;
  public String name;

  public String getAsJson() {
    return new Gson().toJson(this);
  }
}
