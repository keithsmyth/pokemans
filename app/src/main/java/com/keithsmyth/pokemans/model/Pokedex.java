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

    public long retrieveId() {
      if (resource_uri == null) return 0;
      String uri = resource_uri;
      if (uri.endsWith("/")) uri = uri.substring(0, uri.length() - 1);
      return Long.valueOf(uri.substring(uri.lastIndexOf("/") + 1));
    }
  }
}
