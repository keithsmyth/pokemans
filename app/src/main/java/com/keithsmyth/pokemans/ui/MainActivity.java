package com.keithsmyth.pokemans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.keithsmyth.pokemans.R;


public class MainActivity extends Activity implements FragmentManager.OnBackStackChangedListener,
    PartyFragment.PartyListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      getFragmentManager().addOnBackStackChangedListener(this);
      loadFragment(new PartyFragment(), false);
    }
  }

  @Override public void onPokemonPicked(long pokemonId) {
    loadFragment(PokemonFragment.instantiate(pokemonId), true);
  }

  private void loadFragment(Fragment fragment, boolean addToBackStack) {
    FragmentTransaction transaction = getFragmentManager().beginTransaction()
        .replace(R.id.container, fragment);
    if (addToBackStack) {
      transaction.addToBackStack(fragment.getClass().getSimpleName());
    }
    transaction.commit();
  }

  @Override public void onBackStackChanged() {
    if (getActionBar() == null) return;
    getActionBar().setDisplayHomeAsUpEnabled(getFragmentManager().getBackStackEntryCount() > 0);
  }

  @Override public boolean onNavigateUp() {
    getFragmentManager().popBackStack();
    return true;
  }
}
