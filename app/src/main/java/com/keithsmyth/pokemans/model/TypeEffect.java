package com.keithsmyth.pokemans.model;

import android.graphics.Color;

/**
 * @author keithsmyth
 */
public class TypeEffect {

  public String name;
  public int colour;
  public double attackEffect;
  public double defenceEffect;

  public TypeEffect(String name, String colourHex) {
    this.name = name;
    this.colour = Color.parseColor(colourHex);
    attackEffect = 1;
    defenceEffect = 1;
  }
}
