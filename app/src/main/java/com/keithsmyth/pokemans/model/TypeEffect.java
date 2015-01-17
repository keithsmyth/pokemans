package com.keithsmyth.pokemans.model;

import android.graphics.Color;

/**
 * @author keithsmyth
 */
public class TypeEffect {

  public String name;
  public Color colorResId;
  public double attackEffect;
  public double defenceEffect;

  public TypeEffect(String name) {
    this.name = name;
    attackEffect = 1;
    defenceEffect = 1;
  }
}
