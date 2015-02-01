package com.keithsmyth.pokemans.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Pokemon;
import com.keithsmyth.pokemans.ui.MovesListFragment;
import com.keithsmyth.pokemans.ui.TypesListFragment;

/**
 * @author keithsmyth
 */
public class PokemonPagerAdapter extends FragmentPagerAdapter {

  private static final int NUM_VIEWS = 2;
  private final Pokemon pokemon;

  public PokemonPagerAdapter(FragmentManager fm, Pokemon pokemon) {
    super(fm);
    this.pokemon = pokemon;
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return TypesListFragment.instantiate(pokemon);
    }
    return MovesListFragment.instantiate(pokemon);
  }

  @Override public CharSequence getPageTitle(int position) {
    if (position == 0) {
      return App.getResourceString(R.string.pokemon_types_title);
    }
    return App.getResourceString(R.string.pokemon_moves_title);
  }

  @Override public int getCount() {
    return NUM_VIEWS;
  }
}
