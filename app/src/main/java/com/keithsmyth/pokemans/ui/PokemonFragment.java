package com.keithsmyth.pokemans.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.adapter.PokemonPagerAdapter;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Pokemon;

/**
 * @author keithsmyth
 */
public class PokemonFragment extends BaseDataFragment<Pokemon> {

  private static final String EXTRA_URI = "extra-uri";
  private static final String EXTRA_ID = "extra-id";

  private TextView nameText;
  private TextView evoText;
  private ViewPager pager;

  public static PokemonFragment instantiate(String uri) {
    Bundle bundle = new Bundle();
    bundle.putString(EXTRA_URI, uri);
    PokemonFragment fragment = new PokemonFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  public static PokemonFragment instantiate(Long id) {
    Bundle bundle = new Bundle();
    bundle.putLong(EXTRA_ID, id);
    PokemonFragment fragment = new PokemonFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override protected Class<Pokemon> getModelType() {
    return Pokemon.class;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                               Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_pokemon, container, false);
    nameText = (TextView) view.findViewById(R.id.txt_name);
    evoText = (TextView) view.findViewById(R.id.txt_evo);
    pager = (ViewPager) view.findViewById(R.id.pager);
    return view;
  }

  @Override protected void setLoading(boolean isLoading) {
    super.setLoading(isLoading);
    if (getView() == null) return;
    pager.setVisibility(isLoading ? View.GONE : View.VISIBLE);
  }

  @Override protected void requestData() {
    Bundle bundle = getArguments();
    if (bundle == null) return;
    // can be passed id or uri
    String uri = bundle.getString(EXTRA_URI, null);
    if (TextUtils.isEmpty(uri)) {
      Long id = bundle.getLong(EXTRA_ID);
      App.getPokemonData().getPokemon(id, this);
    } else {
      App.getPokemonData().getPokemon(uri, this);
    }
  }

  @Override protected void populate(Pokemon model) {
    if (getView() == null) return;
    nameText.setText(model.name);
    loadEvolutions(model);
    pager.setAdapter(new PokemonPagerAdapter(getChildFragmentManager(), model));
  }

  private void loadEvolutions(Pokemon pokemon) {
    if (pokemon.evolutions.isEmpty()) return;
    evoText.setText(pokemon.evolutions.get(0).toString());
  }

}
