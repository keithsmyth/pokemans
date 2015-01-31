package com.keithsmyth.pokemans.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Party;
import com.keithsmyth.pokemans.model.Pokedex;

/**
 * @author keithsmyth
 */
public class PickActivity extends FragmentActivity implements PickFragment.PickListener {

  public static final String LOOKUP_KEY = "LOOKUP_KEY";
  public static final String POKEMON_KEY = "POKEMON_KEY";

  public static Intent forParty(Context context) {
    return createIntent(context, false);
  }

  public static Intent forLookup(Context context) {
    return createIntent(context, true);
  }

  private static Intent createIntent(Context context, boolean forLookup) {
    Intent intent = new Intent(context, PickActivity.class);
    intent.putExtra(LOOKUP_KEY, forLookup);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, new PickFragment())
          .commit();
    }
  }

  @Override public void onPokemonPicked(Pokedex.Pokemon pokemon) {
    if (isLookupMode()) {
      // open the details fragment
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, PokemonFragment.instantiate(pokemon.resource_uri))
          .addToBackStack(PokemonFragment.class.getName())
          .commit();
      return;
    }
    // return back to caller
    Intent intent = new Intent();
    intent.putExtra(POKEMON_KEY, Party.Member.fromPokemon(pokemon).getAsJson());
    setResult(RESULT_OK, intent);
    finish();
  }

  private boolean isLookupMode() {
    return getIntent().getBooleanExtra(LOOKUP_KEY, false);
  }
}
