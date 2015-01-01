package com.keithsmyth.pokemans.model;

import java.util.List;

/**
 * @author keithsmyth
 */
public class Pokemon {
  public String name;

  public List<PokeType> types;

  public List<Evolution> evolutions;

  public static class PokeType {
    public String name;
    public String resource_uri;
  }

  public static class Evolution {
    public int level;
    public String method;
    public String resource_uri;
    public String to;
  }
}
