package com.keithsmyth.pokemans.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Party;
import com.keithsmyth.pokemans.model.Pokedex;

/**
 * @author keithsmyth
 */
public class PickActivity extends Activity implements PickFragment.PickListener {

  public static final String POKEMON_KEY = "POKEMON_KEY";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(R.id.container, new PickFragment())
          .commit();
    }
  }

  @Override public void onPokemonPicked(Pokedex.Pokemon pokemon) {
    Intent intent = new Intent();
    intent.putExtra(POKEMON_KEY, Party.Member.fromPokemon(pokemon).getAsJson());
    setResult(RESULT_OK, intent);
    finish();
  }
}
