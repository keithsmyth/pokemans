package com.keithsmyth.pokemans.model;

import java.util.List;

/**
 * @author keithsmyth
 */
public class PokeType {
  public String name;

  public List<Lookup> ineffective;

  public List<Lookup> no_effect;

  public List<Lookup> resistance;

  public List<Lookup> super_effective;

  public List<Lookup> weakness;
}
