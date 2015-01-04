package com.keithsmyth.pokemans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.Utils;
import com.keithsmyth.pokemans.adapter.PokemonAdapter;
import com.keithsmyth.pokemans.model.Pokedex;

import java.util.List;

/**
 * @author keithsmyth
 */
public class PickFragment extends Fragment {

  private EditText filterText;
  private RecyclerView recyclerView;
  private PokemonAdapter adapter;
  private PickListener pickListener;

  public PickFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_pick, container, false);
    filterText = (EditText) view.findViewById(R.id.txt_filter);
    filterText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        trySetFilter(s.toString());
      }

      @Override public void afterTextChanged(Editable s) {
      }
    });

    view.findViewById(R.id.btn_clear_filter).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        filterText.setText(null);
      }
    });

    recyclerView = (RecyclerView) view.findViewById(R.id.lst_pokemon);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setHasFixedSize(true);
    return view;
  }

  private void trySetFilter(String filterString) {
    if (adapter == null) return;
    adapter.setFilter(filterString);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof PickListener) {
      pickListener = (PickListener) activity;
    }
  }

  @Override public void onStart() {
    super.onStart();
    setLoading(true);

    App.getPokemonData().getPokedex(new com.keithsmyth.pokemans.data.Callback<Pokedex>() {
      @Override public void onSuccess(Pokedex model) {
        setLoading(false);
        setupPokemon(model.pokemon);
      }

      @Override public void onFail(String msg) {
        setLoading(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override public void onStop() {
    super.onStop();
    Utils.closeKeyboard(getActivity(), filterText);
  }

  private void setLoading(boolean loading) {
    if (getView() == null) return;
    getView().findViewById(R.id.progress).setVisibility(loading ? View.VISIBLE : View.GONE);
    getView().findViewById(R.id.lst_pokemon).setVisibility(loading ? View.GONE : View.VISIBLE);
  }

  private void setupPokemon(final List<Pokedex.Pokemon> pokemonList) {
    if (recyclerView == null) return;
    adapter = new PokemonAdapter(pokemonList, new PokemonAdapter
        .PokemonItemClickListener() {
      @Override public void onClick(Pokedex.Pokemon pokemon) {
        // pass back up to activity
        pickListener.onPokemonPicked(pokemon);
      }
    });
    adapter.setFilter(filterText.getText().toString());
    recyclerView.setAdapter(adapter);
  }

  public static interface PickListener {
    public void onPokemonPicked(Pokedex.Pokemon pokemon);
  }

}
