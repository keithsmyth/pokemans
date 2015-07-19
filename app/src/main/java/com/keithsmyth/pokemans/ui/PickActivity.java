package com.keithsmyth.pokemans.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.PartyMember;
import com.keithsmyth.pokemans.model.Pokedex;
import com.keithsmyth.pokemans.model.Pokemon;

/**
 * @author keithsmyth
 */
public class PickActivity extends AppCompatActivity implements PickFragment.PickListener,
    MovesListFragment.MoveListFragmentListener {

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
      setTitle(isLookupMode() ? R.string.pick_name : R.string.pick_name_party);
      loadFragment(new PickFragment(), false);
    }
  }

  private void loadFragment(Fragment fragment, boolean addToBackStack) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    if (addToBackStack) {
      transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
      transaction.addToBackStack(fragment.getClass().getSimpleName());
    }
    transaction.replace(R.id.container, fragment);
    transaction.commit();
  }

  @Override public void onPokemonPicked(Pokedex.Pokemon pokemon) {
    if (isLookupMode()) {
      // open the details fragment
      loadFragment(PokemonFragment.instantiate(pokemon.resource_uri), true);
      return;
    }
    // return back to caller
    Intent intent = new Intent();
    intent.putExtra(POKEMON_KEY, PartyMember.fromPokemon(pokemon).getAsJson());
    setResult(RESULT_OK, intent);
    finish();
  }

  private boolean isLookupMode() {
    return getIntent().getBooleanExtra(LOOKUP_KEY, false);
  }

  @Override public void onMoveClicked(Pokemon.Move move) {
    loadFragment(MoveFragment.create(move.resource_uri), true);
  }
}
