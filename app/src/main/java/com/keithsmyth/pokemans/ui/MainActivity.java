package com.keithsmyth.pokemans.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.keithsmyth.pokemans.R;


public class MainActivity extends FragmentActivity implements
    FragmentManager.OnBackStackChangedListener, PartyFragment.PartyListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      getSupportFragmentManager().addOnBackStackChangedListener(this);
      loadFragment(new PartyFragment(), false);
    }
  }

  @Override public void onPokemonPicked(long pokemonId) {
    loadFragment(PokemonFragment.instantiate(pokemonId), true);
  }

  private void loadFragment(Fragment fragment, boolean addToBackStack) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, fragment);
    if (addToBackStack) {
      transaction.addToBackStack(fragment.getClass().getSimpleName());
    }
    transaction.commit();
  }

  @Override public void onBackStackChanged() {
    if (getActionBar() == null) return;
    getActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
  }

  @Override public boolean onNavigateUp() {
    getSupportFragmentManager().popBackStack();
    return true;
  }
}
