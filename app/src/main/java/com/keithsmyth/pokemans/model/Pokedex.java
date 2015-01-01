package com.keithsmyth.pokemans.model;

import java.util.List;

/**
 * @author keithsmyth
 */
public class Pokedex {

  public List<Pokemon> pokemon;

  public static class Pokemon {
    public String name;

    public String resource_uri;
  }
}
