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

  private final TypesListFragment typesListFragment;
  private final MovesListFragment movesListFragment;

  public PokemonPagerAdapter(FragmentManager fm) {
    super(fm);
    typesListFragment = new TypesListFragment();
    movesListFragment = new MovesListFragment();
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return typesListFragment;
    }
    return movesListFragment;
  }

  @Override public CharSequence getPageTitle(int position) {
    if (position == 0) {
      return App.getResourceString(R.string.pokemon_types_title);
    }
    return App.getResourceString(R.string.pokemon_moves_title);
  }

  @Override public int getCount() {
    return 2;
  }

  public void populate(Pokemon pokemon) {
    typesListFragment.loadTypes(pokemon.types);
    movesListFragment.loadMoves(pokemon.moves);
  }
}
