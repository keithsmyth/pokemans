package com.keithsmyth.pokemans.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.adapter.MovesAdapter;
import com.keithsmyth.pokemans.adapter.TypeEffectAdapter;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Pokemon;

/**
 * @author keithsmyth
 */
public class PokemonFragment extends Fragment {

  private static final String EXTRA_URI = "extra-uri";
  private static final String EXTRA_ID = "extra-id";

  private TextView nameText;
  private TextView typeText;
  private TextView evoText;
  private RecyclerView movesRecycleView;
  private RecyclerView typeRecycleView;

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

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                               Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_pokemon, container, false);
    nameText = (TextView) view.findViewById(R.id.txt_name);
    evoText = (TextView) view.findViewById(R.id.txt_evo);
    typeText = (TextView) view.findViewById(R.id.txt_types);
    movesRecycleView = (RecyclerView) view.findViewById(R.id.lst_moves);
    movesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    typeRecycleView = (RecyclerView) view.findViewById(R.id.lst_types);
    typeRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3)); //todo: dimens
    return view;
  }

  @Override public void onStart() {
    super.onStart();

    Bundle bundle = getArguments();
    if (bundle == null) return;

    setLoading(true);
    Callback<Pokemon> callback = new Callback<Pokemon>() {
      @Override public void onSuccess(Pokemon model) {
        setLoading(false);
        populate(model);
      }

      @Override public void onFail(String msg) {
        setLoading(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
      }
    };

    // can be passed id or uri
    String uri = bundle.getString(EXTRA_URI, null);
    if (TextUtils.isEmpty(uri)) {
      Long id = bundle.getLong(EXTRA_ID);
      App.getPokemonData().getPokemon(id, callback);
    } else {
      App.getPokemonData().getPokemon(uri, callback);
    }
  }

  private void setLoading(boolean loading) {
    if (getView() == null) return;
    getView().findViewById(R.id.progress).setVisibility(loading ? View.VISIBLE : View.GONE);
  }

  private void populate(Pokemon pokemon) {
    if (getView() == null) return;
    nameText.setText(pokemon.name);
    loadMoves(pokemon);
    loadEvolutions(pokemon);
    loadTypes(pokemon);
  }

  private void loadEvolutions(Pokemon pokemon) {
    if (pokemon.evolutions.isEmpty()) return;
    evoText.setText(pokemon.evolutions.get(0).toString());
  }

  private void loadMoves(Pokemon pokemon) {
    movesRecycleView.setAdapter(new MovesAdapter(pokemon.moves));
  }

  private void loadTypes(Pokemon pokemon) {
    // list names
    StringBuilder types = new StringBuilder();
    for (Pokemon.PokeType pokeType : pokemon.types) {
      if (types.length() > 0) types.append(", ");
      types.append(pokeType.name);
    }
    typeText.setText(types.toString());
    // load type attackEffect
    final TypeEffectAdapter adapter = new TypeEffectAdapter(pokemon.types);
    adapter.init(new TypeEffectAdapter.TypeEffectInitListener() {
      @Override public void onReady() {
        typeRecycleView.setAdapter(adapter);
      }
    });
  }

}
