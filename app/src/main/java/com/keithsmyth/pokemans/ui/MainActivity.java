package com.keithsmyth.pokemans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Pokedex;


public class MainActivity extends Activity implements PickFragment.PickListener {

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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onPokemonPicked(Pokedex.Pokemon pokemon) {
    Fragment fragment = new PokemonFragment();
    fragment.setArguments(PokemonFragment.getArguments(pokemon.resource_uri));
    loadFragment(fragment, true);
  }
}
