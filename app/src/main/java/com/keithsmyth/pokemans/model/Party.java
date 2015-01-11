package com.keithsmyth.pokemans.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keithsmyth
 */
public class Party {

  public static Party fromJson(String json) {
    return json == null ? new Party() : new Gson().fromJson(json, Party.class);
  }

  public final List<Member> memberList;

  public Party() {
    memberList = new ArrayList<>();
  }

  public String getAsJson() {
    return new Gson().toJson(this);
  }

  public static class Member {

    public static Member fromPokemon(Pokedex.Pokemon pokemon) {
      Member member = new Member();
      member.id = pokemon.retrieveId();
      member.name = pokemon.name;
      return member;
    }

    public static Member fromJson(String json) {
      return json == null ? new Member() : new Gson().fromJson(json, Member.class);
    }

    public long id;
    public String name;

    public String getAsJson() {
      return new Gson().toJson(this);
    }
  }
}
