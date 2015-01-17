package com.keithsmyth.pokemans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Pokedex;


public class MainActivity extends Activity implements PartyFragment.PartyListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      loadFragment(new PartyFragment(), false);
    }
  }

  private void loadFragment(Fragment fragment, boolean addToBackStack) {
    FragmentTransaction transaction = getFragmentManager().beginTransaction()
        .replace(R.id.container, fragment);
    if (addToBackStack) {
      transaction.addToBackStack(fragment.getClass().getSimpleName());
    }
    transaction.commit();
  }

  @Override public void onPokemonPicked(long pokemonId) {
    loadFragment(PokemonFragment.instantiate(pokemonId), true);
  }
}
