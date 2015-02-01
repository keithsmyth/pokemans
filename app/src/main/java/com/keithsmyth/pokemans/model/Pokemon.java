package com.keithsmyth.pokemans.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author keithsmyth
 */
public class Pokemon {

  public static Pokemon fromJson(String json) {
    return json == null ? new Pokemon() : new Gson().fromJson(json, Pokemon.class);
  }

  public String name;

  public long national_id;

  public String resource_uri;

  public List<PokeType> types;

  public List<Evolution> evolutions;

  public List<Move> moves;

  public String getAsJson() {
    return new Gson().toJson(this);
  }

  public static class PokeType {
    public String name;
    public String resource_uri;
  }

  public static class Evolution {
    public int level;
    public String method;
    public String resource_uri;
    public String to;

    @Override public String toString() {
      return String.format("%1$s %2$s %3$s",
          to,
          method.replace("_", " "),
          level != 0 ? String.valueOf(level) : "");
    }
  }

  public static class Move {
    public String learn_type;
    public String name;
    public Integer level;
    public String resource_uri;
  }
}
