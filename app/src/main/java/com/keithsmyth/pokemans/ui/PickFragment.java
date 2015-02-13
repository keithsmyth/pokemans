package com.keithsmyth.pokemans.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class PickFragment extends BaseDataFragment<Pokedex> {

  private EditText filterText;
  private RecyclerView recyclerView;
  private PokemonAdapter adapter;
  private PickListener pickListener;

  public PickFragment() {
  }

  @Override protected Class<Pokedex> getModelType() {
    return Pokedex.class;
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

  @Override protected void requestData() {
    App.getPokemonData().getPokedex(this);
  }

  @Override public void onStop() {
    super.onStop();
    Utils.closeKeyboard(getActivity(), filterText);
  }

  @Override protected void populate(Pokedex model) {
    adapter = new PokemonAdapter(model.pokemon, new PokemonAdapter
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
