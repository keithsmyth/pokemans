package com.keithsmyth.pokemans.model;

import java.util.List;

/**
 * @author keithsmyth
 */
public class Pokemon {
  public String name;

  public List<PokeType> types;

  public static class PokeType {
    public String name;
    public String resource_uri;
  }
}
