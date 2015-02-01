package com.keithsmyth.pokemans.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.adapter.MovesAdapter;
import com.keithsmyth.pokemans.model.Pokemon;

import java.util.List;

/**
 * @author keithsmyth
 */
public class MovesListFragment extends Fragment {

  private static final String KEY_POKEMON_MODEL = "pokemon-model";

  public static MovesListFragment instantiate(Pokemon pokemon) {
    MovesListFragment fragment = new MovesListFragment();
    Bundle args = new Bundle();
    args.putString(KEY_POKEMON_MODEL,  pokemon.getAsJson());
    fragment.setArguments(args);
    return fragment;
  }

  private RecyclerView movesRecycleView;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_moves_list, container, false);
    movesRecycleView = (RecyclerView) view.findViewById(R.id.lst_moves);
    movesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    loadMoves();
    return view;
  }

  private void loadMoves() {
    Pokemon pokemon = Pokemon.fromJson(getArguments().getString(KEY_POKEMON_MODEL));
    movesRecycleView.setAdapter(new MovesAdapter(pokemon.moves));
  }
}
