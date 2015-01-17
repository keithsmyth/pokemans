package com.keithsmyth.pokemans.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.adapter.EvolutionAdapter;
import com.keithsmyth.pokemans.adapter.PokeTypeAdapter;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Pokemon;

import java.util.ArrayList;

/**
 * @author keithsmyth
 */
public class PokemonFragment extends Fragment {

  private static final String EXTRA_URI = "extra-uri";

  public static PokemonFragment instantiate(String uri) {
    Bundle bundle = new Bundle();
    bundle.putString(EXTRA_URI, uri);
    PokemonFragment fragment = new PokemonFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  private TextView nameText;
  private RecyclerView typesList;
  private RecyclerView evolutionsList;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                               Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_pokemon, container, false);

    nameText = (TextView) view.findViewById(R.id.txt_name);

    typesList = (RecyclerView) view.findViewById(R.id.lst_types);
    typesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    typesList.setAdapter(new PokeTypeAdapter(new ArrayList<Pokemon.PokeType>()));

    evolutionsList = (RecyclerView) view.findViewById(R.id.lst_evolutions);
    evolutionsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    evolutionsList.setAdapter(new EvolutionAdapter(new ArrayList<Pokemon.Evolution>()));

    return view;
  }

  @Override public void onStart() {
    super.onStart();

    Bundle bundle = getArguments();
    if (bundle == null) return;

    setLoading(true);
    App.getPokemonData().getPokemon(bundle.getString(EXTRA_URI), new Callback<Pokemon>() {
      @Override public void onSuccess(Pokemon model) {
        setLoading(false);
        populate(model);
      }

      @Override public void onFail(String msg) {
        setLoading(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void setLoading(boolean loading) {
    if (getView() == null) return;
    getView().findViewById(R.id.progress).setVisibility(loading ? View.VISIBLE : View.GONE);
  }

  private void populate(Pokemon pokemon) {
    if (getView() == null) return;
    nameText.setText(pokemon.name);
    typesList.setAdapter(new PokeTypeAdapter(pokemon.types));
    evolutionsList.setAdapter(new EvolutionAdapter(pokemon.evolutions));
  }

}
